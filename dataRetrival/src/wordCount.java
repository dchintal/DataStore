import java.io.FileInputStream;


public class wordCount {
	public static void main(){
	
	 InputStream fis=new FileInputStream("abcd.ppt");
     HSLFSlideShow show=new HSLFSlideShow(fis);
     SlideShow ss=new SlideShow(show);
     Slide[] slides=ss.getSlides();
     StringBuilder builder = new StringBuilder();
     for(int x=0; x < slides.length; x++)
     {
         TextRun[] runs = slides[x].getTextRuns();
         for(int j=0; j<runs.length; j++) {
             TextRun run = runs[j];
             if(run != null) {
                 String text = run.getText();
                 builder.append(text);
             }
         }
     }
	}
}
