package game.level.tiles;

public class TilePresets {

    private static int[][] presets = new int[256][4];

    public TilePresets() {
        for (int i = 0; i < presets.length; i++) {
            presets[i][0] = 0;
            presets[i][1] = 0;
            presets[i][2] = 0;
            presets[i][3] = 0;
        }

        // 7, 8, 13, 14 = middle 4 tiles

        addArray(0, new int[] { 0, 3, 18, 21 });

        addArray(16, new int[] { 12, 15, 18, 21 });
        addArray(32, new int[] { 0, 1, 18, 19 });
        addArray(34, new int[] { 0, 1, 18, 19 });
        addArray(48, new int[] { 6, 22, 18, 19 });
        addArray(49, new int[] { 12, 13, 18, 19 });
        addArray(64, new int[] { 0, 3, 6, 9 });
        addArray(72, new int[] { 0, 3, 6, 9 });
        addArray(80, new int[] { 6, 9, 12, 15 });
        addArray(96, new int[] { 0, 2, 12, 16 });
        addArray(98, new int[] { 0, 1, 6, 7 });

        addArray(112, new int[] { 6, 22, 12, 16 });
        addArray(113, new int[] { 6, 7, 12, 16 });
        addArray(114, new int[] { 6, 22, 12, 14 });
        addArray(115, new int[] { 6, 7, 12, 13 });
        addArray(128, new int[] { 2, 3, 20, 21 });
        addArray(144, new int[] { 23, 9, 19, 21 });
        addArray(152, new int[] { 14, 15, 20, 21 });
        addArray(160, new int[] { 1, 2, 19, 20 });
        addArray(176, new int[] { 23, 22, 19, 20 });
        addArray(177, new int[] { 23, 8, 19, 20 });
        addArray(184, new int[] { 7, 22, 19, 20 });
        addArray(185, new int[] { 13, 14, 19, 20 });
        addArray(192, new int[] { 2, 3, 17, 15 });
        addArray(196, new int[] { 2, 3, 8, 9 });

        addArray(208, new int[] { 23, 9, 17, 15 });
        addArray(212, new int[] { 23, 9, 13, 15 });
        addArray(216, new int[] { 14, 9, 17, 15 });
        addArray(220, new int[] { 8, 9, 14, 15 });
        addArray(224, new int[] { 1, 2, 17, 16 });
        addArray(226, new int[] { 1, 2, 17, 14 });
        addArray(228, new int[] { 1, 2, 7, 16 });
        addArray(230, new int[] { 1, 2, 7, 8 });
        addArray(240, new int[] { 23, 22, 17, 16 });
        addArray(241, new int[] { 23, 8, 17, 16 });
        addArray(242, new int[] { 23, 22, 17, 14 });
        addArray(243, new int[] { 23, 8, 17, 14 });
        addArray(244, new int[] { 23, 22, 13, 16 });
        addArray(245, new int[] { 23, 8, 13, 16 });
        addArray(246, new int[] { 23, 22, 13, 14 });
        addArray(247, new int[] { 23, 8, 13, 14 });
        addArray(248, new int[] { 7, 22, 17, 16 });
        addArray(249, new int[] { 7, 8, 17, 16 });
        addArray(250, new int[] { 7, 22, 17, 14 });
        addArray(251, new int[] { 7, 8, 17, 14 });
        addArray(252, new int[] { 7, 22, 13, 16 });
        addArray(253, new int[] { 7, 8, 13, 16 });
        addArray(254, new int[] { 7, 22, 13, 7 });
        addArray(255, new int[] { 7, 8, 13, 14 });
    }

    private void addArray(int loc, int[] ar) {
        presets[loc] = ar;
    }

    public static int[] getTiles(int id) {
        return presets[id];
    }

}
