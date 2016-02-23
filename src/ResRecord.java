
public class ResRecord {
	private byte name;
	private Rdata resource;
	private byte rdata;
	
	public byte getName() {
		return name;
	}
	public void setName(byte name) {
		this.name = name;
	}
	public Rdata getResource() {
		return resource;
	}
	public void setResource(Rdata resource) {
		this.resource = resource;
	}
	public byte getRdata() {
		return rdata;
	}
	public void setRdata(byte rdata) {
		this.rdata = rdata;
	}
}
