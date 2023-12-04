package proiectdiz.Model.DataFormat;

public class RequestObject {
    private String action;
    private Data data;



    public static class Data {
        private String dataType;
        private String dataContent;



        @Override
        public String toString() {
            return "{" +
                    "dataType='" + dataType + '\'' +
                    ", dataContent='" + dataContent + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "{" +
                "action='" + action + '\'' +
                ", data=" + data +
                '}';
    }
}
