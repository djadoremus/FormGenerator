package ph.adoremus.formgenerator.callback;

import org.json.JSONObject;

import ph.adoremus.formgenerator.serializable.JSONSerializable;

public interface FormCallback {
	public void call(JSONSerializable response);
}
