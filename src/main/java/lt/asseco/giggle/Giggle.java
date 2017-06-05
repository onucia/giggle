package lt.asseco.giggle;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	public static String ARG_TABLE = "table";
	public static String ARG_SCHEMA = "schema";
	public static String ARG_EXTENDED = "extended";
	
	private Log log = LogFactory.getLog( "<Giggle>" );

    @Autowired
    private DBMetadataService dbMetaSrv;
    
    @Autowired
    private TableInfoService tiService;
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Giggle.class, args);
    }
    
    @Override
    public void run(ApplicationArguments args) {
    	log.info( "Start" );
        
        String resultFilePrefix = ((args.getOptionValues( ARG_TABLE ) != null) && !args.getOptionValues( ARG_TABLE ).isEmpty() ) ? args.getOptionValues( ARG_TABLE ).get( 0 ) : "";
        
        String schemaPattern = ((args.getOptionValues( ARG_SCHEMA ) != null) && !args.getOptionValues( ARG_SCHEMA ).isEmpty() ) ? args.getOptionValues( ARG_SCHEMA ).get( 0 ) + "%" : null;
        String tablePattern = resultFilePrefix + "%";
        
        boolean extendedInfo = args.getOptionValues( ARG_EXTENDED ) != null;
        
        List<TableInfo> result = dbMetaSrv.getAllTables( schemaPattern, tablePattern );
        for ( TableInfo ti : result ) {
            fillSingleTableInfo( ti, extendedInfo );
        }
        
        //save result to file. 
        tiService.storeTableInfoList( resultFilePrefix, result );
        
        log.info( "End" );

    }

    int counter = 0;
    public void fillSingleTableInfo( TableInfo tableInfo, boolean extendedInfo ) {
    	log.info( ++counter + " " + tableInfo.getTableType() + " : " + tableInfo.getIdentificator() );
        
        String schemaName = tableInfo.getSchemaName();
        String tableName = tableInfo.getTableName();
        
        tableInfo.setColumns( dbMetaSrv.getDestDBTableColumns( schemaName, tableName ) );
        
        if ( "TABLE".equalsIgnoreCase( tableInfo.getTableType() ) ) {
            tableInfo.setPrimaryKeys( dbMetaSrv.getPrimaryKeys( schemaName, tableName ) );
            tableInfo.setUniqueIndexes( dbMetaSrv.getUniqueIndexes( schemaName, tableName ) );
            if ( extendedInfo ) {
                tableInfo.setExportedKeys( dbMetaSrv.getExportedKeys( schemaName, tableName ) );
                tableInfo.setImportedKeys( dbMetaSrv.getImportedKeys( schemaName, tableName ) );
            	
            }
        }
    }

    
}
