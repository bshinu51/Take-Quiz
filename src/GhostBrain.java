import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

public class GhostBrain implements Observer {
	BlackBoard blackBoard = BlackBoard.getInstance();
	Temprature temp = new Temprature();
	Calendar time = Calendar.getInstance();
	int hour;
	boolean isCurrentAnswerCorrect;
	String correctnessMessage = "Let's start...";
	double correctnessPercentage = 0;
	String ghostEmotion = "indiffrent";
	

	public GhostBrain() {
		blackBoard.addObserver(this);
	}
	public String getTemprature() {
		return temp.getTemp();
	}
	public String getSystemCurentTime() {
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}
	public String getCompanionMessage() {
		return getGreeingMessage() + ", " + getCorrectnessMessage(isCurrentAnswerCorrect) ;
	}

	private String getGreeingMessage() {
		hour = time.get(Calendar.HOUR_OF_DAY);
		String greetingMessage = null;

		if (hour < 12)
			greetingMessage = "Good Morning";
         else if (hour < 17 && !(hour == 12))
        	 greetingMessage = "Good Afternoon";
    	 else if (hour == 12)
    		 greetingMessage = "Good Noon";
    	 else
    		 greetingMessage = "Good Evening";	
    	 return greetingMessage;
    }
	/**
    public String getTempMessage() {
    	double temperture = Double.parseDouble(temp.getTemp());
    	String tempertureMessage = null;
    	if (temperture < 45)
    		tempertureMessage = "It's cold";
        else if (temperture > 46 && hour > 85)
        	tempertureMessage = "It's nice out there";
   	 	else if (temperture > 86)
   	 		tempertureMessage = "It's hot out there";
   	 	else
   	 		tempertureMessage = temp.getTemp();
   		 
    	return tempertureMessage;
    }
    */
    private String getCorrectnessMessage (boolean isCorrect) {
    	if(isCorrect)
    		return "You are correct!";
    	else
    		return "Try again!";
    }

    public String getGhostCharacter () {
    	if (correctnessPercentage < 50)
    		ghostEmotion = "sad";
    	else if(correctnessPercentage > 80)
    		ghostEmotion = "happy";
    	else
    		ghostEmotion = "indifferent";
    	
		return ghostEmotion;
    }
  
	@Override
	public void update(Observable o, Object arg) {
		// Is the answered question correct
		isCurrentAnswerCorrect = ((BlackBoard) o).getTable().get(arg).getEntryCorrectness();
		System.out.println("Your answer is " + isCurrentAnswerCorrect);

	   correctnessPercentage = ((BlackBoard) o).calculateTableCorrectness();
	   System.out.println("Ghost is " + getGhostCharacter());
		
		// iterate through table entry and set companion message

	}
}
