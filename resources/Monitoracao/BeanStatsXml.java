package br.com.splgenerator.web.servlets.monitoring.xml;

public class BeanStatsXml {

	private int selectFuncionarios;
	private int insertFuncionarios;
	private int updateFuncionarios;
	private int deleteFuncionarios;
	
	public int getSelectFuncionarios() {
		return selectFuncionarios;
	}
	public void setSelectFuncionarios(int selectFuncionarios) {
		this.selectFuncionarios = selectFuncionarios;
	}
	public int getInsertFuncionarios() {
		return insertFuncionarios;
	}
	public void setInsertFuncionarios(int insertFuncionarios) {
		this.insertFuncionarios = insertFuncionarios;
	}
	public int getUpdateFuncionarios() {
		return updateFuncionarios;
	}
	public void setUpdateFuncionarios(int updateFuncionarios) {
		this.updateFuncionarios = updateFuncionarios;
	}
	public int getDeleteFuncionarios() {
		return deleteFuncionarios;
	}
	public void setDeleteFuncionarios(int deleteFuncionarios) {
		this.deleteFuncionarios = deleteFuncionarios;
	}

}
