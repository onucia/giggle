package lt.asseco.giggle.meta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class DBMetadataServiceImpl implements DBMetadataService {
	
	private static Log log = LogFactory.getLog(DBMetadataService.class);

    @Autowired
    private SqlSession sqlSession;
    
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    
    /*
     * CACHE
     */
    /** <schema.table> -> List of relation */
    private Map<String,List<RelationMetadata>> tableImportedKeys = new HashMap<>();
    /** <schema.table> -> List of relation */
    private Map<String,List<RelationMetadata>> tableExportedKeys = new HashMap<>();
    /** <schema.table> -> primary key set */
    private Map<String,Set<String>> tablePrimaryKeys = new HashMap<>();
    /** <schema.table> -> uniques indexes set */
    private Map<String,Set<String>> tableUniqueIndexes = new HashMap<>();
    
    private Connection getConnection() {
        return sqlSession.getConnection();
    }
    
    @Override
    public List<DBColumn> getDestDBTableColumns( String schemaName, String tableName ) {
        List<DBColumn> columns = new LinkedList<>();
        try {
            ResultSet rs = getConnection().getMetaData().getColumns( null, schemaName, tableName, null );
            while( rs.next() ) {
            	DBColumn dbCol = new DBColumn();
            	dbCol.setName( rs.getString( "COLUMN_NAME" ) );
            	dbCol.setType( rs.getString( "TYPE_NAME" ));
            	dbCol.setSize( rs.getString( "COLUMN_SIZE" ));
            	dbCol.setNullable( rs.getString( "NULLABLE" ));
            	dbCol.setRemarks( rs.getString( "REMARKS" ));
            	
            	columns.add( dbCol );
//                System.out.printf( "[%d] %s: %s.%s | %s\n", i++, rs.getString( "TABLE_CAT"),  rs.getString( "TABLE_SCHEM"), rs.getString( "TABLE_NAME"), rs.getString( "COLUMN_NAME") );
            }
            rs.close();
        } catch ( SQLException e ) {
            throw new RuntimeException( String.format( "Klaida traukiant schemos '%s' lenteles '%s' stulpeliu pavadinimus", schemaName, tableName ) );
        }
        return columns;
    }

    @Override
    public String getFKColumnName ( String relationSchemaName, String relationTableName, String referencedSchemaName, String referencedTableName ) {
        String fkColumnName = null;
        try {
            ResultSet rs = getConnection().getMetaData().getCrossReference( null, referencedSchemaName, referencedTableName, null, relationSchemaName, relationTableName );
            while( rs.next() ) {
                fkColumnName = rs.getString( "FKCOLUMN_NAME" );
            }
            rs.close();
       
        } catch ( SQLException e ) {
            throw new RuntimeException( String.format( "Klaida traukiant schemos '%s' lenteles '%s' sarysius", relationSchemaName, relationTableName ) );
        }
        return fkColumnName;
    }

    @Override
    public Set<String> checkIfAllTablesExits(String schemaName, Collection<String> listFromTable ) {
        Set<String> notExistingTables = new HashSet<>();
        for ( String tableName : listFromTable ) { 
            if ( !checkifTableExists( schemaName, tableName ) )
                notExistingTables.add( tableName );
        }        
        
        return notExistingTables;
    }
    
    @Override
    public boolean checkifTableExists(String schemaName, String tableName) {
        boolean found = false;
        try {
            ResultSet rs = getConnection().getMetaData().getTables(null, schemaName, tableName, null);
            found = rs.next(); //jeigu result sete yra nors viena rezultatu eilute, vadinasi tokia lentele rasta.
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Klaida ieškant lentelės '%s' schemoje '%s'", tableName, schemaName));
        }
        return found;
    }
    
    @Override
    public List<RelationMetadata> getImportedKeys ( String schemaName, String tableName ) {
        String tableWithSchema = schemaName+"."+tableName;
        if ( !tableImportedKeys.containsKey( tableWithSchema ) ) {
            List<RelationMetadata> result = new LinkedList<>();
            try {
                ResultSet rs = getConnection().getMetaData().getImportedKeys( null, schemaName, tableName );
                while( rs.next() ) {
                    result.add( new RelationMetadata( rs.getString( "FKTABLE_SCHEM" ), rs.getString( "FKTABLE_NAME" ), rs.getString( "FKCOLUMN_NAME" ), rs.getString( "PKTABLE_SCHEM" ), rs.getString( "PKTABLE_NAME" ), rs.getString( "PKCOLUMN_NAME" ) ) );
                }
                rs.close();
           
            } catch ( Exception e ) {
                throw new RuntimeException(String.format("Klaida ieškant kokie foreignKey yra lentelėje %s.%s", schemaName, tableName));
            }
            tableImportedKeys.put( tableWithSchema, result );
        }
        return new LinkedList<>(tableImportedKeys.get( tableWithSchema ));
    }
    
    
    @Override
    public List<RelationMetadata> getExportedKeys ( String schemaName, String tableName ) {
        String tableWithSchema = schemaName+"."+tableName;
        if ( !tableExportedKeys.containsKey( tableWithSchema )) {
            List<RelationMetadata> result = new LinkedList<>();
            
            try {
                ResultSet rs = getConnection().getMetaData().getExportedKeys( null, schemaName, tableName );
                while( rs.next() ) {
                    result.add( new RelationMetadata( rs.getString( "FKTABLE_SCHEM" ), rs.getString( "FKTABLE_NAME" ), rs.getString( "FKCOLUMN_NAME" ), rs.getString( "PKTABLE_SCHEM" ), rs.getString( "PKTABLE_NAME" ), rs.getString( "PKCOLUMN_NAME" ) ) );
                }
                rs.close();
           
            } catch ( Exception e ) {
                throw new RuntimeException(String.format("Klaida ieškant iš kur yra foreignKey į nurodytą lentelę %s.%s", schemaName, tableName));
            }
            tableExportedKeys.put( tableWithSchema, result );
        }
        return new LinkedList<>( tableExportedKeys.get( tableWithSchema ) );

    }
    
    @Override
    public Set<String> getPrimaryKeys( String schemaName, String tableName ) {
        String tableWithSchema = schemaName+"."+tableName;
        if ( !tablePrimaryKeys.containsKey( tableWithSchema )) {
            Set<String> result = new HashSet<>();
            try {
                ResultSet rs = getConnection().getMetaData().getPrimaryKeys( null, schemaName, tableName );
                while( rs.next() ) {
                    result.add( rs.getString( "COLUMN_NAME" ) );
                }
                rs.close();
           
            } catch ( Exception e ) {
                //throw new RuntimeException(String.format("Klaida ieškant primaryKey schemoje '%s', lenteleje '%s'", schemaName, tableName));
            	log.error(String.format("Klaida ieškant primaryKey schemoje '%s', lenteleje '%s'", schemaName, tableName));
            }
            tablePrimaryKeys.put( tableWithSchema, result );
        }
        return new HashSet<>( tablePrimaryKeys.get( tableWithSchema ) ); 
    }

    
    @Override
    public Set<String> getUniqueIndexes( String schemaName, String tableName ) {
        String tableWithSchema = schemaName+"."+tableName;
        if ( !tableUniqueIndexes.containsKey( tableWithSchema )) {
            Set<String> result = new HashSet<>();
            try {
                ResultSet rs = getConnection().getMetaData().getIndexInfo( null, schemaName, tableName, true, true );
                while( rs.next() ) {
                    String indexColumn = rs.getString( "COLUMN_NAME" );
                    if ( !StringUtils.isEmpty( indexColumn ) )
                        result.add( indexColumn );
                }
                rs.close();
           
            } catch ( Exception e ) {
                //throw new RuntimeException(String.format("Klaida ieškant unique index schemoje '%s', lenteleje '%s'", schemaName, tableName));
            	log.error(String.format("Klaida ieškant unique index schemoje '%s', lenteleje '%s'", schemaName, tableName));
            }
            tableUniqueIndexes.put( tableWithSchema, result );
        }
        return new HashSet<>( tableUniqueIndexes.get( tableWithSchema ) ); 
    }
    
    @Override
    public List<TableInfo> getAllTables( String schemaNameCriteria, String tableNameCriteria ) {
        List<TableInfo> result = new LinkedList<TableInfo>();
        try {
            ResultSet rs = getConnection().getMetaData().getTables( null, schemaNameCriteria, tableNameCriteria, null );
            while( rs.next() ) {
                String tableType = rs.getString( "TABLE_TYPE" );
                String tableSchema = rs.getString( "TABLE_SCHEM" );
                String tableName = rs.getString( "TABLE_NAME" );
                String remarks = rs.getString( "REMARKS" );
                result.add( new TableInfo( tableType, tableSchema, tableName, remarks ) );
            }
            rs.close();
        } catch ( Exception e ) {
            throw new RuntimeException("Klaida ieskant visu lenteliu saraso", e);
        }        
        
        return result;
    }    
    
}
