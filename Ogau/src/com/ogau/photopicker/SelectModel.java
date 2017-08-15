package com.ogau.photopicker;

/**
 * 照片选择类型
 * @author guanxiqin
 *
 */
public enum SelectModel {
	SINGLE(Constants.MODE_SINGLE), 
	MULTI(Constants.MODE_MULTI);
	private int modle;

	SelectModel(int modle) {
		this.modle = modle;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.modle);
	}
}
