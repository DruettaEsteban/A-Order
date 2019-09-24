package Graphs;

class PercentualValues {
    private int APercentual;
    private int BPercentual;
    private int CPercentual;
    private int DPercentual;

    PercentualValues(float a, float b, float c, float d){
        float max = a + b + c + d;

        APercentual = (int) (100.0 * (a/max));
        BPercentual = (int) (100.0 * (b/max));
        CPercentual = (int) (100.0 * (c/max));
        DPercentual = (int) (100.0 * (d/max));
    }

    int getAPercentual() {
        return APercentual;
    }

    int getBPercentual() {
        return BPercentual;
    }

    int getCPercentual() {
        return CPercentual;
    }

    int getDPercentual() {
        return DPercentual;
    }
}
