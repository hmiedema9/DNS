import java.io.IOException;
import java.nio.ByteBuffer;
/*
* Question class
* CIS 457 Project 2 part 1
* Kevin Anderson, Brett Greenman, Jonathan Powers
* This class is where the question is stored to be appended to the header. 
*/
public class Question {
	private byte[] qName = new byte [256];
	private short qType;
	private short qClass;
	private String questionName;

	public short getqType() {
		return qType;
	}

	public void setqType(short qType) {
		this.qType = qType;
	}

	public short getqClass() {
		return qClass;
	}

	public void setqClass(short qClass) {
		this.qClass = qClass;
	}

	public String getqName() {
		return questionName;
	}
	//This method adds the question to byte array. 
	public byte[] toBytes() throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(new byte[260]);
		
		buf.put(qName);		
		buf.putShort(qType);
		buf.putShort(qClass);

		return buf.array();
	}
	//This method gets the byte buffer and interprets the question from it. 
	public Question fromBytes(ByteBuffer buf) {
		String name = "";
		int index = 0;
		byte item = buf.get();
		qName[index] = item;
		index++;
		while(item != 0){
			
			for(int i = 0; i < (int) item; i++){
				byte temp = buf.get();
				qName[index] = item;
				index++;
				name += (char) temp;
			}
			
			item = buf.get();
			qName[index] = item;
			index++;
			if(item != 0){
				name += ".";
			}
		}
		
		questionName = name;
		qType = buf.getShort();
		qClass = buf.getShort();
		return this;
	}
	//To string method used for error checking. 
	public String toString(){
		return  "name: " + questionName + " " +
				"type: " + qType + " " +
				"class: " + qClass;
	}
}