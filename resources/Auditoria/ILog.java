package br.com.splgenerator.audit;

import java.io.Serializable;

public interface ILog extends Serializable {
	
	void log(String level, String msg);
	
}
