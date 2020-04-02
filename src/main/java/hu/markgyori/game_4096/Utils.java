package hu.markgyori.game_4096;

public class Utils {
	/**
	 * This function get the block basic color by point.
	 * @param num
	 * @return The block color.
	 */
	public static String GetColorByBlock(int num) {
		int c = 0;
		while((num = num / 2) >= 1)
			c++;
		
		return Config.colors[c];
	}
	
	/**
	 * This function get the block text color by point.
	 * @param num
	 * @return The label text color.
	 */
	public static String GetTextColorByBlock(int num) {
		String base = GetColorByBlock(num);
		
		int color = (int)Long.parseLong(base.substring(1), 16);
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;
		
		return String.format("#%02x%02x%02x", 255 - r, 255 - g, 255 - b);
	}
}