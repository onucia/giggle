package lt.asseco.giggle.meta;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface DBMetadataService {

    /**
     * Pateikia stulpeliu informacija pagal nurodyta schemaName ir tableName
     * @param schemaName
     * @param tableName
     * @return
     */
    public List<DBColumn> getDestDBTableColumns( String schemaName, String tableName );

    /**
     * 
     * @param relationSchemaName - schemos pavadinimas tos lentelės, kurioje FK 
     * @param relationTableName - lentelės pavadinimas, kurioje FK
     * @param referencedSchemaName - schemos pavadinimas, tos lentelės, į kurią rodo FK
     * @param referencedTableName - lentelės pavadinimas, į kurią rodo FK
     * 
     * @return FK stulpelio pavadinimas (column name) sąryšių lentelėje
     */
    public String getFKColumnName( String relationSchemaName, String relationTableName, String referencedSchemaName, String referencedTableName );
    
    /**
     * Patikrina ar schemoje egzistuoja lenteles. Grazina neegzistuojanciu lenteliu sarasa
     * @param schemaName
     * @param listFromTable
     * @return
     */
    public Set<String> checkIfAllTablesExits( String schemaName, Collection<String> listFromTable );
    
    /**
     * Patikrina, as egzistuoja nurodyta lentelė
     * 
     * @param schemaName schemos, kurioje turėtų būti lentelė, pavadinimas
     * @param tableName lentelės pavadinimas
     * @return egzistavimo požymis: true - egzistuoja, false - tokios lentelės nėra 
     */
    public boolean checkifTableExists(String schemaName, String tableName);

    /**
     * Nurodytai lentelei išrenkami visi foreign key
     * @param schemaName
     * @param tableName
     * @return
     */
    public List<RelationMetadata> getImportedKeys ( String schemaName, String tableName );
    
    /**
     * Išrenkama iš kokių lentelių yra FK į nurodytą lentelę
     * @param schemaName
     * @param tableName
     * @return
     */
    public List<RelationMetadata> getExportedKeys ( String schemaName, String tableName );
    
    /**
     * Gražina lentelės pirminius raktus
     */
    public Set<String> getPrimaryKeys( String schemaName, String tableName );

    /**
     * Gražina lentelės unikalius indeksus
     */
    public Set<String> getUniqueIndexes( String schemaName, String tableName );

    List<TableInfo> getAllTables( String schemaNameCriteria, String tableNameCriteria );

    
    
}
