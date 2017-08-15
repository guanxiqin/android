package com.ogau.photopicker;

import java.util.List;

public class Folder {
	public String name;
	public String path;
	public Image cover;
	public List<Image> images;

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		try {
			Folder other=(Folder) obj;
			return this.path.equalsIgnoreCase(other.path);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		return super.equals(obj);
	}
}
