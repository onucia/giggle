package lt.asseco.giggle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lt.asseco.giggle.meta.DBMetadataService;
import lt.asseco.giggle.meta.TableInfo;
import lt.asseco.giggle.services.TableInfoService;

@SpringBootApplication
public class Giggle implements ApplicationRunner {

    @Autowired
    private DBMetadataService dbMetaSrv;
    
    @Autowired
    private TableInfoService tiService;
    
    @Override
    public void run(ApplicationArguments args) {
        System.out.println( "Start" );
        
        String resultFilePrefix = ((args.getOptionValues( "table" ) != null) && !args.getOptionValues( "table" ).isEmpty() ) ? args.getOptionValues( "table" ).get( 0 ) : "";
        
        String schemaPattern = ((args.getOptionValues( "schema" ) != null) && !args.getOptionValues( "schema" ).isEmpty() ) ? args.getOptionValues( "schema" ).get( 0 ) : null;
        String tablePattern = resultFilePrefix + "%";
        
        List<TableInfo> result = extractAllTableInfo( schemaPattern, tablePattern );
        //save result to file. 
        tiService.storeTableInfoList( resultFilePrefix, result );
        
        System.out.println( "End" );

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Giggle.class, args);
    }
    
    
    public List<TableInfo> extractAllTableInfo ( String schemaPattern, String tableNamePattern ) {
        List<TableInfo> result = dbMetaSrv.getAllTables( schemaPattern, tableNamePattern );
        for ( TableInfo ti : result ) {
            fillSingleTableInfo( ti );
        }
        
        return result;
    }
    
    
    int counter = 0;
    public void fillSingleTableInfo( TableInfo tableInfo ) {
        System.out.println( ++counter + " " + tableInfo.getTableType() + " : " + tableInfo.getIdentificator() );
        
        String schemaName = tableInfo.getSchemaName();
        String tableName = tableInfo.getTableName();
        
        tableInfo.setColumns( dbMetaSrv.getDestDBTableColumns( schemaName, tableName ) );
//        System.out.println( "Stulpeliai: " + tableInfo.getColumns() );
        
        if ( "TABLE".equalsIgnoreCase( tableInfo.getTableType() ) ) {
            tableInfo.setExportedKeys( dbMetaSrv.getExportedKeys( schemaName, tableName ) );
            tableInfo.setImportedKeys( dbMetaSrv.getImportedKeys( schemaName, tableName ) );
            tableInfo.setPrimaryKeys( dbMetaSrv.getPrimaryKeys( schemaName, tableName ) );
            tableInfo.setUniqueIndexes( dbMetaSrv.getUniqueIndexes( schemaName, tableName ) );
        }
    }

    
}
