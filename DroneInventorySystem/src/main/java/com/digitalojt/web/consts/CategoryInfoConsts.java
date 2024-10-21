package com.digitalojt.web.consts;
/**
 * 分類情報管理 定数クラス
 * 
 * @author KaitoDokan
 */
//public class CategoryInfoConsts {
//	
//	public static final String FRAME = "フレーム";
//	
//	public static final String PROPELLER = "プロペラ";
//	
//	public static final String ELECTRIC_MOTOR = "電動モーター";
//	
//	public static final String ELECTRIC_SPEED_CONTROLLER = "電子速度調整器";
//	
//	public static final String BATTERY = "バッテリー";
//	
//	public static final String FLIGHT_CONTROLLER = "フライトコントローラー";
//	
//	public static final String REMOTE_CONTROLLER = "リモートコントローラー";
//	
//	public static final String RECEIVER = "受信機";
//	
//	public static final String GPS_MODULE = "GPSモジュール";
//	
//	public static final String CAMERA_SENSOR = "カメラ/センサー";
//}

public enum CategoryInfoConsts{
	
	FRAME("フレーム"),
	PROPELLER("プロペラ"),
	ELECTRIC_MOTOR("電動モーター"),
	ELECTRIC_SPEED_CONTROLLER("電子速度調整器"),
	BATTERY("バッテリー"),
	FLIGHT_CONTROLLER("フライトコントローラー"),
	REMOTE_CONTROLLER("リモートコントローラー"),
	RECEIVER("受信機"),
	GPS_MODULE("GPSモジュール"),
	CAMERA_SENSOR("カメラ/センサー");
	
	 private final String category;

	CategoryInfoConsts(String category) {
		this.category=category;
	}
	
    public String getName() {
        return category;
    }
}
