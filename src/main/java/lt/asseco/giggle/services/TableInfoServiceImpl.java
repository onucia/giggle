package lt.asseco.giggle.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lt.asseco.giggle.gen.table_info.ColumnType;
import lt.asseco.giggle.gen.table_info.RelationMetadataType;
import lt.asseco.giggle.gen.table_info.TableInfoType;
import lt.asseco.giggle.gen.table_info.TableInfoXml;
import lt.asseco.giggle.meta.DBColumn;
import lt.asseco.giggle.meta.RelationMetadata;
import lt.asseco.giggle.meta.TableInfo;

@Service
public class TableInfoServiceImpl implements TableInfoService {
    
    private Log log = LogFactory.getLog( TableInfoServiceImpl.class );

    private static final String TABLE_INFO_FILE_NAME = "tableInfo.xml";

    private static final String TABLE_INFO_PREVIEW_FILE_NAME = "tableInfoPreview.html";
    
    @Value("${result.dir:c:/tmp}")
    private String resultDir;
    
//    private @Value("${result.xsltfile:classpath:xml/table-info-preview.xsl}") Resource xsltFile;
    private @Value("${result.xsltfile:classpath:xml/table-info-preview-simple.xsl}") Resource xsltFile;
    
    /* (non-Javadoc)
     * @see lt.asseco.giggle.services.TableInfoServiceI#storeTableInfoList(java.util.List)
     */
    @Override
    public void storeTableInfoList( String preffix, List<TableInfo> tableInfoList ) {
        TableInfoXml xml = new TableInfoXml();
        for ( TableInfo ti : tableInfoList ) {
            TableInfoType tiType = new TableInfoType();
            tiType.setSchemaName( ti.getSchemaName() );
            tiType.setTableName( ti.getTableName() );
            
            tiType.setTableType( ti.getTableType() );
            tiType.setRemarks( ti.getRemarks() );
            
            for ( DBColumn column : ti.getColumns() ) {
            	ColumnType colType = transformColumnType( column );
                tiType.getColumn().add( colType );
            }
            
            for ( String pkName : ti.getPrimaryKeys() ) {
                tiType.getPrimaryKey().add( pkName );
            }

            for ( String idx : ti.getUniqueIndexes() ) {
                tiType.getUniqueIndex().add( idx );
            }
            
            for ( RelationMetadata rm : ti.getImportedKeys() ) {
                RelationMetadataType rmType = transformRelationMetadata( rm );
                tiType.getImportedKeyList().add( rmType );
            }

            for ( RelationMetadata rm : ti.getExportedKeys() ) {
                RelationMetadataType rmType = transformRelationMetadata( rm );
                tiType.getExportedKeyList().add( rmType );
            }
            
            xml.getTableInfo().add( tiType );
        }
        
        String filePrefix = StringUtils.isNotEmpty(preffix) ? preffix : "";
        
        storeToFile( filePrefix, xml );
    }
    
    private RelationMetadataType transformRelationMetadata( RelationMetadata rm ) {
        RelationMetadataType rmType = new RelationMetadataType();
        
        rmType.setForeignColumn( rm.getForeignColumn() );
        rmType.setForeignSchema( rm.getForeignSchema() );
        rmType.setForeignTable( rm.getForeignTable() );
        rmType.setPrimaryColumn( rm.getPrimaryColumn() );
        rmType.setPrimarySchema( rm.getPrimarySchema() );
        rmType.setPrimaryTable( rm.getPrimaryTable() );
        
        return rmType;
    }
    
    private ColumnType transformColumnType( DBColumn column ) {
    	ColumnType colType = new ColumnType();
    	
    	colType.setName( column.getName() );
    	colType.setType( column.getType() );
    	colType.setSize( column.getSize() );
    	colType.setNullable( column.getNullable() );
    	colType.setRemarks( column.getRemarks() );
    	
    	return colType;
    }
    
    
    private void storeToFile( String filePrefix, TableInfoXml xml )  {
        try {
            File file = new File(resultDir + File.separator + filePrefix + TABLE_INFO_FILE_NAME);
            log.info( "Will store result to file: " + file.getAbsolutePath());
            JAXBContext jaxbContext = JAXBContext.newInstance(TableInfoXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(xml, file);
            
            // Create Transformer
            TransformerFactory tf = TransformerFactory.newInstance();
            StreamSource xslt = new StreamSource( xsltFile.getInputStream() );
        
            Transformer transformer = tf.newTransformer(xslt);
            
            // Source
            JAXBSource source = new JAXBSource(jaxbContext, xml);
     
            // Result
            StreamResult result = new StreamResult( new File(resultDir + File.separator + filePrefix + TABLE_INFO_PREVIEW_FILE_NAME ) );
            
            transformer.transform(source, result);
            
          } catch (JAXBException e) {
              log.error( "Klaida išsaugant į failą " + filePrefix + TABLE_INFO_FILE_NAME, e );
          } catch ( TransformerConfigurationException e ) {
              log.error( "Nepavyko transformacija su XSLT", e );
          } catch ( TransformerException e ) {
              log.error( e, e );
          } catch ( IOException e ) {
              log.error( e, e );
        }
    }
    
}
