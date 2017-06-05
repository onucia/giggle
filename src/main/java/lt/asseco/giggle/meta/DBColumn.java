package lt.asseco.giggle.meta;

public class DBColumn {

	private String name;
	private String type;
	private String size;
	private String nullable;
	private String remarks;
	
	public DBColumn() {}
	
	public DBColumn(String name, String type, String size, String nullable, String remarks) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.nullable = nullable;
		this.remarks = remarks;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
