package kavax.microedition.lcdui;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

    public class Splash extends Canvas
    {
    public MIDtxt midlet;
    public Display display;
    public Alert alert;
    public int code;
    public int menu=0;
    public Image[] image;
       public Image[] BG;
       public Image[] img;
      public int ps;

    public Splash(MIDtxt midlet)
    {
    this.midlet=midlet;
    display=Display.getDisplay(midlet);
   ps=0;
    try
    {
    Image w1 = Image.createImage("/kavax/Image/w1.png");
    Image w2 = Image.createImage("/kavax/Image/w2.png");
     Image w3 = Image.createImage("/kavax/Image/w3.png");
        Image w4 = Image.createImage("/kavax/Image/w4.png");
    Image w5 = Image.createImage("/kavax/Image/w5.png");
     Image w6 = Image.createImage("/kavax/Image/w6.png");
        Image w7 = Image.createImage("/kavax/Image/w7.png");
    image = new Image[]{w1,w2,w3,w4,w5,w6,w7};
       }
    catch (Exception e)
    {
    ps=1;
    }
    try{
        Image t1 = Image.createImage("/kavax/Image/t1.png");
    Image t2 = Image.createImage("/kavax/Image/t2.png");
     Image t3 = Image.createImage("/kavax/Image/t3.png");
          Image t4 = Image.createImage("/kavax/Image/t4.png");
    Image t5 = Image.createImage("/kavax/Image/t5.png");
     Image t6 = Image.createImage("/kavax/Image/t6.png");
        Image t7 = Image.createImage("/kavax/Image/t7.png");
      img=new Image[]{t1,t2,t3,t4,t5,t6,t7};
         }
    catch (Exception e)
    {
    ps=ps+1;
    }
    try{
    Image bg = Image.createImage("/kavax/Image/BG.png");
    BG = new Image[]{bg};
    }
    catch (Exception e)
    {
    }
    System.out.println(ps+"");
   
    }

    public void paint(Graphics g)
    {
    setFullScreenMode(true);
    g.setColor(0x000000);
    g.fillRect(0, 0, getWidth(), getHeight());
    try{
    g.drawImage(BG[0],getWidth()/2,getHeight()/2,3);
    }catch(Exception e){}
    try{
    g.drawImage(img[menu],getWidth()/2,getHeight()-130,3);
        }catch(Exception e){}
        try{
    g.drawImage(image[menu],getWidth()/2,getHeight()-30,3);
    }catch(Exception e){}
     if (ps==2){
    new Slist(1);
    }
    }

    protected void keyPressed(int c)
    {
    MIDtxt.lastDy=MIDtxt.dp.getCurrent();
    try{
    code=c;
    if (c == KEY_NUM0){
    new Slist(0);
    }
    if (c == KEY_NUM1){
   new Listener().ksk();
    }
    if (c == KEY_NUM3){
   new Lock().startApp();
    }
    if (c == KEY_NUM7){
    new Listener().tst();
    }
    if (c == KEY_NUM9){
    MIDtxt.lastD1 = MIDtxt.dp.getCurrent();
				 new chen.c();
    }
    if (code==-4||code==54||code==-2||code==56)
    {
    if(menu>=0&menu<6) menu++;
    else
    {
    if(menu==6) menu=0;
    }
    }
    if (code==-3||code==52||code==-1||code==50)
    {
    if(menu>0&menu<=6) menu--;
    else
    {
    if(menu==0) menu=6;
    }
    }
    if (code==-5||code==53) menukey();
    repaint();
    }catch(Exception e){
    midlet.back();
    }
    }

    public void menukey()
    {
    Listener ll=new Listener();
    switch(menu)
    {
    case 0:
    ll.fsSet(1, "功能中心(Fuck Q！)");
    break;
    case 1:
    ll.fsSet(2, "拓展功能(Fuck Q！)");
    break;
    case 2:
      ll.fsSet(5, "实用工具(Fuck Q！)");
    break;
    case 3:
      ll.fsSet(4, "3D屏保(Fuck Q！)");
    break;
    case 4:
      ll.fsSet(3, "经典游戏(Fuck Q！)");
    break;
    case 5:
     alert();
    break;
    case 6:
     midlet.back();
    break;
    }
    }

    public void alert()
    {
    alert = new Alert("关于", "作者:GhostGzt(Gentle)\nQQ:1275731466\nE-Mail:GhostGzt@163.com\n编辑器:Notepad+WTK\nSuper Text 第五版(内核: kavaText 4.21)\n合法商标: Fuck Q！\nFuck Q！保留一切解释权利！！！", null, AlertType.INFO);
    alert.setTimeout(2560);
    display.setCurrent(alert);
    }
}
