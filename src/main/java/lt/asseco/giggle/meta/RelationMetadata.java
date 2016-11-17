package lt.asseco.giggle.meta;

/**
 * <ul>
 * <li>foreign* - lenteles, kurioje yra foreign key, duomenys</li>
 * <li>primary* - lenteles, i kuria rodo foreign key, duomenys </li>
 * </ul>
 */

public class RelationMetadata {

    private String foreignSchema;
    private String foreignTable;
    private String foreignColumn;
    
    private String primarySchema;
    private String primaryTable;
    private String primaryColumn;
    
    public RelationMetadata( String foreignSchema, String foreignTable, String foreignColumn, String primarySchema, String primaryTable,
            String primaryColumn ) {
        super();
        this.foreignSchema = foreignSchema;
        this.foreignTable = foreignTable;
        this.foreignColumn = foreignColumn;
        this.primarySchema = primarySchema;
        this.primaryTable = primaryTable;
        this.primaryColumn = primaryColumn;
    }
    
    public String getForeignSchema() {
        return foreignSchema;
    }
    public String getForeignTable() {
        return foreignTable;
    }
    public String getForeignColumn() {
        return foreignColumn;
    }
    public String getPrimarySchema() {
        return primarySchema;
    }
    public String getPrimaryTable() {
        return primaryTable;
    }
    public String getPrimaryColumn() {
        return primaryColumn;
    }
    
    public String getPrimaryTableWithSchema () {
        return primarySchema + "." + primaryTable; 
    }

    public String getForeignTableWithSchema () {
        return foreignSchema + "." + foreignTable; 
    }

    @Override
    public String toString() {
        return String.format( "Primary: %s[%s]; Foreign %s[%s].", getPrimaryTableWithSchema(), primaryColumn, getForeignTableWithSchema(), foreignColumn );
    }
}
