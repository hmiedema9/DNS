import java.nio.ByteBuffer;
public class Question {
    private String domainString;
	private short qType;
	private short qClass;
    private ByteBuffer question;
    public Question(String message, short type, short c){
         domainString = message;
         qType = type;
         qClass = c;
         updateQuestion();
    }
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
        updateQuestion();
	}
    public String getdomainString(){
        return domainString;
    }
    public void setdomainString(String in){
        this.domainString = in;
        updateQuestion();
    }

    //This method is run whenever an instance variable is changed so the
    private void updateQuestion(){
        this.question.allocate(400);
        String[] tokens = domainString.split("[.]");
        for(String token : tokens){
            question.putShort((short) token.length());
            question.put(token.getBytes());
        }
        question.putShort((short) 0);
        question.putShort(qType);
        question.putShort(qClass);
    }

    public ByteBuffer getQuestion(){
        return question;
    }

}