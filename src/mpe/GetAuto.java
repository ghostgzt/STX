package mpe;

//import jclazz.core.Clazz;
//import jclazz.core.ClazzException;
//import jclazz.decompiler.*;
public class GetAuto {

    public GetAuto() {
    }

    public String[] Auto(String file) {
        String list[] = new String[0];
        /*    try {
        clazz = new Clazz("/api/" + file + ".clazz");
        ClazzSourceView csv = new ClazzSourceView(clazz, null);
        csv.setDecompileParameters(null);
        String source = csv.getSource();
        list = (new Split()).Splite(source, "\n");
        } catch (ClazzException clazzexception) {
        } catch (Exception exception) {
        }*/
        return list;
    }
    //  private Clazz clazz;
}
