package lt.asseco.giggle.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SimpleMapper {

    @Select ( "SELECT 'table' from dual ")
    public List<String> selectTableNames ();
    
}
