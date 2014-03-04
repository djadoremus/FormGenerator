package ph.adoremus.formgenerator.formatter;

import java.util.ArrayList;
import java.util.LinkedList;

import ph.adoremus.log.Logger;

public class TextFormatter {

	private Logger logger = Logger.getInstance(TextFormatter.class.getName());
	private String format;
	private LinkedList<String> uniqueIndices;
	
	/**
	 * 
	 * @param format (eg. ###-####, $###,###.##)
	 */
	public TextFormatter(String format) {
		this.format = format;
		uniqueIndices = new LinkedList<String>();
		if (format != null){
			for (int i=0; i<format.length(); i++){
				String c = "" + format.charAt(i);
				if (!"#".equals(c)){
					uniqueIndices.add(c);
				}
			}
		}
		logger.debug("uniqueIndices " + uniqueIndices);
	}
	
	public String format(String text){
		if (format != null){
			logger.debug("text " + text + " | format " + format);
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<text.length(); i++){
				String c = "" + format.charAt(i);
				if ((!"#".equals(c))){
					sb.append(uniqueIndices.getFirst());
					uniqueIndices.removeFirst();
				}
				sb.append(text.charAt(i));
				logger.debug(sb.toString());
			}
			return sb.toString();
		} else {
			return text;
		}
	}
}
