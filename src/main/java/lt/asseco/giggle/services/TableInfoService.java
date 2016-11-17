package lt.asseco.giggle.services;

import java.util.List;

import lt.asseco.giggle.meta.TableInfo;

public interface TableInfoService {

    void storeTableInfoList( String tablePattern, List<TableInfo> tableInfoList );

}
