package ph.adoremus.formgenerator.enums;

public enum Type {

	TEXT("text"), PHONE("phone"), NUMBER("number"), PASSWORD("password"), DATEPICKER("datepicker"), 
	RADIOBUTTONS("radiobutton"), CHECKBOXES("checkbox");

	private String value;

	private Type(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static Type getValue(String value){
		if (Type.TEXT.getValue().equals(value)){
			return Type.TEXT;
		} else if (Type.PHONE.getValue().equals(value)){
			return Type.PHONE;
		} else if (Type.NUMBER.getValue().equals(value)){
			return Type.NUMBER;
		} else if (Type.PASSWORD.getValue().equals(value)){
			return Type.PASSWORD;
		} else if (Type.DATEPICKER.getValue().equals(value)){
			return Type.DATEPICKER;
		} else if (Type.RADIOBUTTONS.getValue().equals(value)){
			return Type.RADIOBUTTONS;
		} else if (Type.CHECKBOXES.getValue().equals(value)){
			return Type.CHECKBOXES;
		} else {
			return null;
		}
	}
}
