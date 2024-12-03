package com.digitalojt.web.util;

import java.util.Arrays;

import com.digitalojt.web.consts.InvalidCharacter;

/**
 * パラメーターチェックに関する処理を行うクラス
 * 
 * @author KaitoDokan
 *
 */
public class ParamCheckUtil {

	/**
	 * 不正文字チェック
	 *  
	 * @param val
	 * @return
	 */
	public static Boolean isParameterInvalid(String val) {

		return Arrays.stream(InvalidCharacter.values())
				.anyMatch(invalidChar -> val.indexOf(invalidChar.getCharacter()) >= 0);
	}

	/**
	 * 半角数字チェック
	 *  
	 * @param val
	 * @return
	 */
	public static Boolean isNumeric(Integer val) {
		
	    if (val == null) {
	        return false;
	    }
	    
		return !val.toString().matches("^[0-9]+$");
	}
}