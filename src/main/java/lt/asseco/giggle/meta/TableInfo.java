package lt.asseco.giggle.meta;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TableInfo {
    
    private String tableType;
    private String schemaName;
    private String tableName;
    private String remarks;
    
    private Set<String> primaryKeys = new HashSet<>();
    private Set<String> uniqueIndexes = new HashSet<>();
    
    private List<DBColumn> columns = new LinkedList<>();
    
    private List<RelationMetadata> importedKeys = new LinkedList<>();
    private List<RelationMetadata> exportedKeys = new LinkedList<>();
    
    public TableInfo() {
        
    }
    
    public TableInfo ( String tableType, String schemaName, String tableName, String remarks ) {
        this.tableType = tableType;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.remarks = remarks;
    }
    
    public String getIdentificator() {
        return schemaName + "." + tableName; 
    }
    
    public String getSchemaName() {
        return schemaName;
    }
    public void setSchemaName( String schemaName ) {
        this.schemaName = schemaName;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName( String tableName ) {
        this.tableName = tableName;
    }
    public Set<String> getPrimaryKeys() {
        return primaryKeys;
    }
    public void setPrimaryKeys( Set<String> primaryKeys ) {
        this.primaryKeys = primaryKeys;
    }
    public Set<String> getUniqueIndexes() {
        return uniqueIndexes;
    }
    public void setUniqueIndexes( Set<String> uniqueIndexes ) {
        this.uniqueIndexes = uniqueIndexes;
    }
    public List<RelationMetadata> getImportedKeys() {
        return importedKeys;
    }
    public void setImportedKeys( List<RelationMetadata> importedKeys ) {
        this.importedKeys = importedKeys;
    }
    public List<RelationMetadata> getExportedKeys() {
        return exportedKeys;
    }
    public void setExportedKeys( List<RelationMetadata> exportedKeys ) {
        this.exportedKeys = exportedKeys;
    }

    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns( List<DBColumn> columns ) {
        this.columns = columns;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType( String tableType ) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks( String remarks ) {
        this.remarks = remarks;
    }

    
    
    
}
