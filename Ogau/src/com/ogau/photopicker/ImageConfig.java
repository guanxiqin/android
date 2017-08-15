package com.ogau.photopicker;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageConfig implements Parcelable {
	public int minWidth;
	public int minHeight;
	public long minSize;
	public String[] mimeType;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.minWidth);
		dest.writeInt(this.minHeight);
		dest.writeLong(this.minSize);
		dest.writeStringArray(this.mimeType);
	}

	public ImageConfig() {

	}

	protected ImageConfig(Parcel in) {
		this.minWidth = in.readInt();
		this.minHeight = in.readInt();
		this.minSize = in.readLong();
		this.mimeType = in.createStringArray();
	}

	public static final Creator<ImageConfig> CREATER = new Creator<ImageConfig>() {

		@Override
		public ImageConfig createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ImageConfig(source);
		}

		@Override
		public ImageConfig[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ImageConfig[size];
		}
	};
}
