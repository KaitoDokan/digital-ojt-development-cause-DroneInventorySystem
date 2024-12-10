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

		return !val.toString().matches("^-?[0-9]+$");
	}

	/**
	 * 数字の範囲チェック (0～99,999,999)
	 *  
	 * @param val
	 * @return
	 */
	public static Boolean isWithinRange(Integer val) {
		// 0～99,999,999の範囲チェック

		return val < 0 || val > 99999999;
	}

	/**
	 * 2つの整数の大小関係チェック (from < to) 
	 *  
	 * @param from
	 * @param to
	 * @return
	 */
	public static Boolean compareFromTo(Integer from, Integer to) {
		// from < to の場合に false、それ以外の場合に true を返す
		return from >= to;
	}
}
