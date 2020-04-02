package hu.markgyori.game_4096;

public class Utils {
	private static String[] colors = new String[] {"#e9faa7", "#49d186", "#25b867", "#187843", "#1d968c", "#1d7096", "#1f2aa3", "#9514db", "#d41eb8", "#e01063", "#ff0a3b", "#940925", "#b52414"};

	/**
	 * This function get the block basic color by point.
	 * @param num
	 * @return The block color.
	 */
	public static String GetColorByBlock(int num) {
		int c = 0;
		while((num = num / 2) >= 1)
			c++;
		
		return colors[c];
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
		
		return "#" + Integer.toHexString(255 - r) + Integer.toHexString(255 - g) + Integer.toHexString(255 - b);
	}
}