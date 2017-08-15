package com.ogau.photopicker;

public class Constants {
	/** 图片选择模式，int类型 */
	public static final String EXTRA_SELECT_MODE = "select_count_mode";
	/** 单选 */
	public static final int MODE_SINGLE = 0;
	/** 多选 */
	public static final int MODE_MULTI = 1;
	/** 最大图片选择次数，int类型 */
	public static final String EXTRA_SELECT_COUNT = "max_select_count";
	/** 默认最大照片数量 */
	public static final int DEFAULT_MAX_TOTAL = 9;
	/** 是否显示相机，boolean类型 */
	public static final String EXTRA_SHOW_CAMERA = "show_camera";
	/** 默认选择的数据集 */
	public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
	/** 筛选照片配置信息 */
	public static final String EXTRA_IMAGE_CONFIG = "image_config";
	/** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合 */
	public static final String EXTRA_RESULT = "select_result";
	//*******************************************************************************************
	public static final String EXTRA_PHOTOS = "extra_photos";
	public static final String EXTRA_CURRENT_ITEM = "extra_current_item";
	/** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合 */
	public static final String EXTRA_RESULT_PRE = "preview_result";
	/** 预览请求状态码 */
	public static final int REQUEST_PREVIEW = 99;
}
