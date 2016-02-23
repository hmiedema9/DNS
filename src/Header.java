import java.io.IOException;
import java.nio.ByteBuffer;

/*
* Header Class
* CIS 457 Project 2 Part 1
* Kevin Anderson, Brett Greenman, Jonathan Powers
* This class stores the header of the DNS request
*/
public class Header {
	private int id;
	private boolean request;
	private short opcode;
	private boolean authoritativeAnswer;
	private boolean truncated;
	private boolean recursion;
	private boolean canRecurse;
	private short responseCode;
	private short questionEntries;
	private short answerEntries;
	private short authorityRecords;
	private short additionalRecords;
	private short flags;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isRequest() {
		return request;
	}

	public void setRequest(boolean request) {
		this.request = request;
	}

	public short getOpcode() {
		return opcode;
	}

	public void setOpcode(short opcode) {
		this.opcode = opcode;
	}

	public boolean isAuthoritativeAnswer() {
		return authoritativeAnswer;
	}

	public void setAuthortativeAnwser(boolean authortativeAnwser) {
		this.authoritativeAnswer = authortativeAnwser;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public boolean isRecursion() {
		return recursion;
	}

	public void setRecursion(boolean recursion) {
		this.recursion = recursion;
	}

	public short getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(short responseCode) {
		this.responseCode = responseCode;
	}

	public short getQuestionEntries() {
		return questionEntries;
	}

	public void setQuestionEntries(short questionEntries) {
		this.questionEntries = questionEntries;
	}

	public short getAnwserEntries() {
		return answerEntries;
	}

	public void setAnwserEntries(short answerEntries) {
		this.answerEntries = answerEntries;
	}

	public short getAuthorityRecords() {
		return authorityRecords;
	}

	public void setAuthorityRecords(short authorityRecords) {
		this.authorityRecords = authorityRecords;
	}

	public short getAdditionalRecords() {
		return additionalRecords;
	}

	public void setAdditionalRecords(short additionalRecords) {
		this.additionalRecords = additionalRecords;
	}

	public void setFlags(short flags){
		this.flags = flags;
	}

	public short getFlags(){
		return flags;
	}

	public byte[] toBytes(){
		ByteBuffer temp = ByteBuffer.wrap(new byte[192]);
		temp.putShort((short)id);
		short tflags = (short) ((request ? 0 : 1) << 15);
		tflags |= (opcode & 0b1111) << 11;
		// AA, TC set in response
		tflags |= (recursion ? 1 : 0) << 8;
		temp.putShort(tflags);
		temp.putShort(questionEntries);
		temp.putShort(answerEntries);
		temp.putShort(authorityRecords);
		temp.putShort(additionalRecords);
		return temp.array();
	}

	public Header fromBytes(ByteBuffer buf) throws IOException {
		id = ((int)(buf.getShort()) & 0xffff);
		int flags = buf.getShort();
		request = ((flags >> 15) & 1) == 0;
		opcode = (short)((flags >> 11) & 0b1111);
		authoritativeAnswer = ((flags >> 10) & 1) == 1;
		truncated = ((flags >> 9) & 1) == 1;
		recursion = ((flags >> 8) & 1) == 1;
		canRecurse = ((flags >> 7) & 1) == 1;
		responseCode = (short)(flags & 0b1111);
		questionEntries = buf.getShort();
		answerEntries = buf.getShort();
		authorityRecords = buf.getShort();
		additionalRecords = buf.getShort();
		return this;
	}
	
	public boolean isCanRecurse() {
		return canRecurse;
	}

	public void setCanRecurse(boolean canRecurse) {
		this.canRecurse = canRecurse;
	}
	
	public String toString(){
		return  "id: " + id + " " +
				"request: " + request + " " +
				"opcode: " + opcode + " " +
				"authoritativeAnswer: " + authoritativeAnswer + " " +
				"truncated: " + truncated + " " +
				"recursion: " + recursion + " " +
				"canRecurse: " + canRecurse + " " +
				"responseCode: " + responseCode + " " +
				"questionEntries: " + questionEntries + " " +
				"answerEntries: " + answerEntries + " " +
				"authorityRecords: " + authorityRecords + " " +
				"additionalRecords: " + additionalRecords + " " +
				"flags: " + flags;
	}
}
