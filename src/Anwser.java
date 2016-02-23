import java.io.IOException;
import java.nio.ByteBuffer;

/*
* Answer class
* CIS 457 Project 2 part 1
* Kevin Anderson, Brett Greenman, Jonathan Powers
* This class is used to store the DNS answer. It will be used to see if the answer is a 
* forward to another DNS server or the actual answer that is needed. 
*/
public class Anwser {
	String name;
	short type;
	short aClass;
	int ttl;
	short rdlength;
	int rdata;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public short getaClass() {
		return aClass;
	}
	public void setaClass(short aClass) {
		this.aClass = aClass;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public short getRdlength() {
		return rdlength;
	}
	public void setRdlength(short rdlength) {
		this.rdlength = rdlength;
	}
	public int getRdata() {
		return rdata;
	}
	public void setRdata(int rdata) {
		this.rdata = rdata;
	}
	
	public byte[] toBytes() throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(new byte[1024]);
		buf.put(name.getBytes());
		buf.putShort(type);
		buf.putShort(aClass);
		buf.putInt(ttl);
		buf.putShort(rdlength);
		buf.putInt(rdata);
		return buf.array();
	}
	
	public Anwser fromBytes(ByteBuffer buf){
		String tempName = "";
		byte item = buf.get();
		while(item != 0){
			for(int i = 0; i < (int) item; i++){
				byte temp = buf.get();
				tempName += (char) temp;
			}
			item = buf.get();
			if(item != 0){
				tempName += ".";
			}
		}
		name = tempName;
		type = buf.getShort();
		aClass = buf.getShort();
		ttl = buf.getInt();
		rdlength = buf.getShort();
		rdata = buf.getInt();	
		return this;
	}
}
