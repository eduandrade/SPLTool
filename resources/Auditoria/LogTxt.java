package br.com.splgenerator.audit;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="logTXT")
@SessionScoped
public class LogTxt implements ILog {

	@Override
	public void log(String level, String msg) {
		System.out.println(level + " - " + msg);
	}

}
