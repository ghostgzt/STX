package mpe;

import javax.microedition.lcdui.*;

public class MPE implements CommandListener {

    public static final int MENU_WORKSPACE = -1;
    public static final int MENU_MAIN = 0;
    public static final int MENU_FILE = 1;
    public static final int MENU_EDIT = 2;
    public static final int MENU_NAVI = 3;
    public static final int MENU_TEMPLATES = 4;
    public static final int MENU_OPTIONS = 5;
    public static final int MENU_LANGUAGE = 13;
    public static final int MENU_INTERFACE = 14;
    public static final int MENU_EDITOR = 15;
    public static final int MENU_SEARCH = 6;
    public static final int MENU_REPLACE = 7;
    public static final int MENU_LINEED = 8;
    public static final int MENU_GOTO = 9;
    public static final int MENU_ADDTEMPLATE = 10;
    public static final int MENU_EDITTEMPLATE = 11;
    public static final int MENU_ABOUT = 12;
    public static final int API = 13;
    private int menu;
    String api[];
    public Display display;
    public Workspace w;
    public List mainMenu;
    private List list;
    private FileManager fm;
    private TextField tfEdit1;
    private TextField tfEdit2;
    private TextField tfDocTemplate;
    private TextField tfDocLine;
    public TextBox tbLineEditor;
    private ChoiceGroup cgSearchVar;
    private ChoiceGroup cgInterface;
    private ChoiceGroup cgFontFace;
    private ChoiceGroup cgFontSize;
    private ChoiceGroup cgLayout;
    private Command cmdSave;
    private Command cmdAddTemplate;
    private Command cmdDelTemplate;
    private Command cmdEditTemplate;
    private Command cancel;
    private Command paste;
    private Command cmdBack;
    private Command cmdOk;
    private Command addapi;
    private String buffer;

    public MPE() {
        api = new String[0];
        buffer = "";
        Database.load();
        Database.loadTemplates();
        display = chen.c.d;
        w = new Workspace(this);
        fm = new FileManager(this);
        if (!Database.rls || Database.lastsession.equals("")) {
            w.createDocument();
        } else {
            fm.restore();
        }
        cancel = new Command("取消选择", 8, 98);
        addapi = new Command("插入", 4, 99);
        cmdBack = new Command("返回", 2, 100);
        cmdOk = new Command("确定", 4, 0);
        paste = new Command("粘帖", 4, 1);
        cmdSave = new Command("保存", 4, 0);
        cmdAddTemplate = new Command("添加模版", 8, 0);
        cmdEditTemplate = new Command("编辑模版", 8, 1);
        cmdDelTemplate = new Command("删除模版", 8, 2);
    }

    public void startApp() {
        showMenu(-1);
    }

    public void pauseApp() {
    }

    private void ViewApi(String clazz) {
        if (api.length > 0) {
            list = new List(clazz + "\u7C7B", 3);
            for (int i = 0; i < api.length; i++) {
                list.append(api[i], null);
            }

            list.addCommand(addapi);
            list.addCommand(cmdBack);
            list.setCommandListener(this);
            display.setCurrent(list);
        } else {
            display.setCurrent(w);
        }
    }

    public void showMenu(int id) {
        menu = id;
        System.out.println(w.getDocument().globalY());
        System.out.println(w.getDocument().vPosition);
        switch (id) {
            case 10: // '\n'
            case 11: // '\013'
            default:
                break;

            case -1: {
                display.setCurrent(w);
                break;
            }

            case 0: // '\0'
            {
                mainMenu = new List("JCreator 2.5", 3);
                mainMenu.append("\u6587\u4EF6", null);
                mainMenu.append("\u7F16\u8F91", null);
                mainMenu.append("\u5BFC\u822A", null);
                mainMenu.append("\u6A21\u7248", null);
                mainMenu.append("\u8BBE\u7F6E", null);
                mainMenu.append("\u5173\u4E8E", null);
                mainMenu.append("\u9000\u51FA", null);
                if (w.getDocument().inSelection) {
                    mainMenu.addCommand(cancel);
                }
                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 1: // '\001'
            {
                mainMenu = new List("\u6587\u4EF6", 3);
                mainMenu.append("\u6253\u5F00", null);
                mainMenu.append("\u4FDD\u5B58", null);
                mainMenu.append("\u53E6\u5B58\u4E3A", null);
                mainMenu.append("\u65B0\u5EFA\u9879\u76EE", null);
                mainMenu.append("\u5173\u95ED", null);
                mainMenu.append("----", null);
                for (int i = 0; i < w.getDocCount(); i++) {
                    mainMenu.append(w.getDocumentNum(i).filename, null);
                }

                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 2: // '\002'
            {
                mainMenu = new List("\u7F16\u8F91", 3);
                mainMenu.append("\u9009\u62E9", null);
                mainMenu.append("\u5BFC\u5165\u5305", null);
                mainMenu.append("\u67E5\u770Bapi", null);
                mainMenu.append("\u590D\u5236", null);
                mainMenu.append("\u526A\u5207", null);
                mainMenu.append("\u7C98\u5E16", null);
                mainMenu.append("\u67E5\u627E", null);
                mainMenu.append("\u66FF\u6362", null);
                mainMenu.append("\u7F16\u8F91\u884C", null);
                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 3: // '\003'
            {
                mainMenu = new List("\u5BFC\u822A", 3);
                mainMenu.append("\u9876\u90E8", null);
                mainMenu.append("\u5C3E\u90E8", null);
                mainMenu.append("\u884C\u9996", null);
                mainMenu.append("\u884C\u5C3E", null);
                mainMenu.append("\u53BB\u5230(n)\u884C", null);
                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 4: // '\004'
            {
                mainMenu = new List("\u6A21\u7248", 3);
                String s[] = Database.getTemplates();
                for (int i = 0; i < s.length; i++) {
                    mainMenu.append(s[i], null);
                }

                mainMenu.addCommand(cmdAddTemplate);
                mainMenu.addCommand(cmdEditTemplate);
                mainMenu.addCommand(cmdDelTemplate);
                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 5: // '\005'
            {
                mainMenu = new List("\u8BBE\u7F6E", 3);
                mainMenu.append("\u8BED\u8A00", null);
                mainMenu.append("\u754C\u9762", null);
                mainMenu.append("\u7F16\u8F91\u5668", null);
                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 12: // '\f'
            {
                Form f = new Form("\u5173\u4E8E");
                f.append("\u5F00\u53D1:\u827E\u529B\u514B\u8F6F\u4EF6\u5DE5\u4F5C\u5BA4\nJCreator 2.5\nwap.agrj.cn\n");
                f.addCommand(cmdBack);
                f.setCommandListener(this);
                display.setCurrent(f);
                break;
            }

            case 6: // '\006'
            {
                Form f = new Form("\u67E5\u627E");
                tfEdit1 = new TextField("\u67E5\u627E\u5B57\u7B26\u4E32", "", 100, 0);
                f.append(tfEdit1);
                cgSearchVar = new ChoiceGroup("\u65B9\u5411", 1);
                cgSearchVar.append("\u4E0B", null);
                cgSearchVar.append("\u4E0A", null);
                cgSearchVar.append("\u4ECE\u9876\u90E8\u5F00\u59CB\u67E5\u627E", null);
                f.append(cgSearchVar);
                f.addCommand(cmdOk);
                f.addCommand(cmdBack);
                f.setCommandListener(this);
                display.setCurrent(f);
                break;
            }

            case 7: // '\007'
            {
                Form f = new Form("\u66FF\u6362");
                tfEdit1 = new TextField("\u67E5\u627E\u5B57\u7B26\u4E32", "", 100, 0);
                f.append(tfEdit1);
                tfEdit2 = new TextField("\u66FF\u6362\u5B57\u7B26\u4E32", "", 100, 0);
                f.append(tfEdit2);
                cgSearchVar = new ChoiceGroup("\u67E5\u627E\u65B9\u5411", 1);
                cgSearchVar.append("\u5411\u4E0B", null);
                cgSearchVar.append("\u5411\u4E0A", null);
                cgSearchVar.append("\u4ECE\u9876\u90E8\u5F00\u59CB\u67E5\u627E", null);
                f.append(cgSearchVar);
                f.addCommand(cmdOk);
                f.addCommand(cmdBack);
                f.setCommandListener(this);
                display.setCurrent(f);
                break;
            }

            case 8: // '\b'
            {
                tbLineEditor = new TextBox("\u7F16\u8F91\u884C", w.getDocument().data.elementAt(w.getDocument().getCursor(1)).toString(), 5000, 0);
                tbLineEditor.addCommand(cmdOk);
                tbLineEditor.addCommand(paste);
                tbLineEditor.addCommand(cmdBack);
                tbLineEditor.setCommandListener(this);
                display.setCurrent(tbLineEditor);
                break;
            }

            case 9: // '\t'
            {
                Form f = new Form("\u8F6C\u5230");
                tfEdit1 = new TextField("\u8F93\u5165\u884C\u53F7 " + (new Integer(w.getDocument().data.size())).toString() + ")", "0", 10, 2);
                f.append(tfEdit1);
                f.addCommand(cmdOk);
                f.addCommand(cmdBack);
                f.setCommandListener(this);
                display.setCurrent(f);
                break;
            }

            case 13: // '\r'
            {
                mainMenu = new List("\u8BED\u8A00", 3);
                mainMenu.append("\u4E2D\u6587", null);
                mainMenu.append("...", null);
                mainMenu.append("...", null);
                mainMenu.addCommand(cmdBack);
                mainMenu.setCommandListener(this);
                display.setCurrent(mainMenu);
                break;
            }

            case 14: // '\016'
            {
                Form f = new Form("\u754C\u9762\u8BBE\u7F6E");
                cgInterface = new ChoiceGroup("\u754C\u9762\u8BBE\u7F6E", 2);
                cgInterface.append("\u663E\u793A\u884C\u6570", null);
                cgInterface.append("\u4FDD\u5B58\u4E0A\u4E00\u6B21\u64CD\u4F5C", null);
                if (Database.linenum) {
                    cgInterface.setSelectedIndex(0, true);
                } else if (Database.rls) {
                    cgInterface.setSelectedIndex(1, true);
                }
                f.append(cgInterface);
                cgFontFace = new ChoiceGroup("\u5B57\u4F53", 1);
                cgFontFace.append("\u7CFB\u7EDF", null);
                cgFontFace.append("\u6BD4\u4F8B", null);
                cgFontFace.append("\u7B49\u5BBD", null);
                cgFontFace.setSelectedIndex(Database.fontFace, true);
                f.append(cgFontFace);
                cgFontSize = new ChoiceGroup("\u5B57\u4F53\u5927\u5C0F", 1);
                cgFontSize.append("\u5C0F", null);
                cgFontSize.append("\u4E2D", null);
                cgFontSize.append("\u5927", null);
                cgFontSize.setSelectedIndex(Database.fontSize, true);
                f.append(cgFontSize);
                f.addCommand(cmdSave);
                f.addCommand(cmdBack);
                f.setCommandListener(this);
                display.setCurrent(f);
                break;
            }

            case 15: // '\017'
            {
                Form f = new Form("\u7F16\u8F91\u5668");
                cgLayout = new ChoiceGroup("\u4EE3\u7801\u9AD8\u4EAE", 1);
                cgLayout.append("\u4F7F\u7528", null);
                cgLayout.append("\u4E0D\u4F7F\u7528", null);
                cgLayout.setSelectedIndex(Database.cyrLayout, true);
                f.append(cgLayout);
                tfDocTemplate = new TextField("\u65B0\u6587\u6863\u6A21\u7248", Database.template, 1000, 0);
                f.append(tfDocTemplate);
                tfDocLine = new TextField("\u540E\u9000(n)\u4E2A\u5B57\u7B26", (new Integer(Database.templLine)).toString(), 5, 2);
                f.append(tfDocLine);
                f.addCommand(paste);
                f.addCommand(cmdSave);
                f.addCommand(cmdBack);
                f.setCommandListener(this);
                display.setCurrent(f);
                break;
            }
        }
    }

    public void destroyApp(boolean unconditional) {

        if (!Database.rls) {
            Database.lastsession = "";
            for (int i = 0; i < w.getDocCount(); i++) {
                Database.lastsession = Database.lastsession + (w.getDocumentNum(i).filename.equals("New") ? "" : w.getDocumentNum(i).filename + "\n");
            }
            if (!Database.lastsession.equals("")) {
                Database.lastsession = Database.lastsession.substring(0, Database.lastsession.length() - 1);
            }
        }
        Database.saveLang();
        Database.saveInterface();
        Database.saveEditor();
        display.setCurrent(chen.c.show);

    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            if (menu == 0) {
                switch (mainMenu.getSelectedIndex()) {
                    case 0: // '\0'
                        showMenu(1);
                        break;

                    case 1: // '\001'
                        showMenu(2);
                        break;

                    case 2: // '\002'
                        showMenu(3);
                        break;

                    case 3: // '\003'
                        showMenu(4);
                        break;

                    case 4: // '\004'
                        showMenu(5);
                        break;

                    case 5: // '\005'
                        showMenu(12);
                        break;

                    case 6: // '\006'
                        destroyApp(false);
                        break;
                }
            } else if (menu == 1) {
                if (mainMenu.getSelectedIndex() < 5) {
                    switch (mainMenu.getSelectedIndex()) {
                        default:
                            break;

                        case 0: // '\0'
                            fm.show(true);
                            break;

                        case 1: // '\001'
                            if (!w.getDocument().filename.equals("")) {
                                fm.save(w.getDocument().filename);
                                break;
                            }
                        // fall through

                        case 2: // '\002'
                            fm.show(false);
                            break;

                        case 3: // '\003'
                            w.createDocument();
                            showMenu(-1);
                            break;

                        case 4: // '\004'
                            w.closeDocument();
                            showMenu(-1);
                            break;
                    }
                } else if (mainMenu.getSelectedIndex() > 5) {
                    w.showDocument(mainMenu.getSelectedIndex() - 6);
                    showMenu(-1);
                }
            } else if (menu == 2) {
                switch (mainMenu.getSelectedIndex()) {
                    case 0: // '\0'
                        w.getDocument().startSelection();
                        showMenu(-1);
                        break;

                    case 1: // '\001'
                        Document.sta = w.getDocument().globalY();
                        w.getDocument().Import();
                        w.getDocument().endSelection();
                        showMenu(-1);
                        break;

                    case 2: // '\002'
                        api = w.getDocument().getApi();
                        w.getDocument().endSelection();
                        ViewApi(w.getDocument().clazz);
                        break;

                    case 3: // '\003'
                        buffer = w.getDocument().Copy();
                        w.getDocument().endSelection();
                        showMenu(-1);
                        break;

                    case 4: // '\004'
                        buffer = w.getDocument().Cut();
                        showMenu(-1);
                        break;

                    case 5: // '\005'
                        w.getDocument().Insert(buffer);
                        showMenu(-1);
                        break;

                    case 6: // '\006'
                        showMenu(6);
                        break;

                    case 7: // '\007'
                        showMenu(7);
                        break;

                    case 8: // '\b'
                        showMenu(8);
                        break;
                }
            } else if (menu == 3) {
                switch (mainMenu.getSelectedIndex()) {
                    case 0: // '\0'
                        w.getDocument().setCursor(0, 0);
                        showMenu(-1);
                        break;

                    case 1: // '\001'
                        w.getDocument().setCursor(w.getDocument().data.elementAt(w.getDocument().data.size() - 1).toString().length(), w.getDocument().data.size() - 1);
                        showMenu(-1);
                        break;

                    case 2: // '\002'
                        w.getDocument().setCursor(0, w.getDocument().getCursor(1));
                        showMenu(-1);
                        break;

                    case 3: // '\003'
                        w.getDocument().setCursor(w.getDocument().data.elementAt(w.getDocument().getCursor(1)).toString().length(), w.getDocument().getCursor(1));
                        showMenu(-1);
                        break;

                    case 4: // '\004'
                        showMenu(9);
                        break;
                }
            } else if (menu == 4 && mainMenu.getSelectedIndex() > -1) {
                Database.insertTemplate(mainMenu.getSelectedIndex(), w.getDocument());
                showMenu(-1);
            } else if (menu == 5) {
                switch (mainMenu.getSelectedIndex()) {
                    case 0: // '\0'
                        showMenu(13);
                        break;

                    case 1: // '\001'
                        showMenu(14);
                        break;

                    case 2: // '\002'
                        showMenu(15);
                        break;
                }
            } else if (menu == 13) {
                switch (mainMenu.getSelectedIndex()) {
                    case 0: // '\0'
                        Database.lang = "english.lang";
                        break;

                    case 1: // '\001'
                        Database.lang = "russian.lang";
                        break;

                    case 2: // '\002'
                        Database.lang = "ukrainian.lang";
                        break;
                }
                showMenu(0);
            }
        } else if (c == paste) {
            if (tfEdit1 != null) {
                try {
                    tfEdit1.setString(buffer);
                } catch (Exception e) {
                    Alert alert = new Alert("\u7C98\u5E16\u5931\u8D25");
                    alert.setString("\u539F\u56E0:" + e.getMessage());
                    alert.setTimeout(2000);
                    display.setCurrent(alert);
                }
            }
            if (tfDocTemplate != null) {
                try {
                    tfDocTemplate.setString(buffer);
                } catch (Exception e) {
                    Alert alert = new Alert("\u7C98\u5E16\u5931\u8D25");
                    alert.setString("\u539F\u56E0:" + e.getMessage());
                    alert.setTimeout(2000);
                    display.setCurrent(alert);
                }
            }
        } else if (c == addapi) {
            System.out.println(api[list.getSelectedIndex()]);
            String str = api[list.getSelectedIndex()];
            if (str.indexOf(" ") != -1) {
                str = str.substring(str.indexOf(" ") + 1);
            }
            w.getDocument().Insert("." + str);
            showMenu(-1);
        } else if (c == cmdBack) {
            switch (menu) {
                case 0: // '\0'
                    showMenu(-1);
                    System.out.println(w.getDocument().globalY());
                    System.gc();
                    break;

                case 1: // '\001'
                case 2: // '\002'
                case 3: // '\003'
                case 5: // '\005'
                case 12: // '\f'
                    showMenu(0);
                    break;

                case 6: // '\006'
                case 7: // '\007'
                case 8: // '\b'
                    tfEdit1 = null;
                    tfEdit2 = null;
                    tbLineEditor = null;
                    showMenu(2);
                    break;

                case 9: // '\t'
                    showMenu(3);
                    break;

                case 10: // '\n'
                case 11: // '\013'
                    tfEdit1 = null;
                    tfEdit2 = null;
                    showMenu(4);
                    break;

                case 4: // '\004'
                    showMenu(0);
                    break;

                case 13: // '\r'
                case 14: // '\016'
                case 15: // '\017'
                    showMenu(5);
                    break;
            }
        } else if (c == cancel) {
            if (w.getDocument().inSelection) {
                w.getDocument().endSelection();
            }
            showMenu(-1);
            w.getDocument().setCursor(0, w.getDocument().globalY());
        } else if (c == cmdOk) {
            switch (menu) {
                default:
                    break;

                case 6: // '\006'
                {
                    Form f = new Form("\u67E5\u627E");
                    f.append("\u6B63\u5728\u67E5\u627E");
                    display.setCurrent(f);
                    for (int i = cgSearchVar.getSelectedIndex() != 2 ? w.getDocument().getCursor(1) : 0; i < w.getDocument().data.size(); i = cgSearchVar.getSelectedIndex() != 1 ? i + 1 : i - 1) {
                        if (w.getDocument().data.elementAt(i).toString().toLowerCase().indexOf(tfEdit1.getString().toLowerCase()) <= -1) {
                            continue;
                        }
                        w.getDocument().setCursor(0, i);
                        break;
                    }

                    showMenu(-1);
                    break;
                }

                case 7: // '\007'
                {
                    Form f = new Form("\u66FF\u6362");
                    f.append("\u6B63\u5728\u66FF\u6362");
                    display.setCurrent(f);
                    for (int i = cgSearchVar.getSelectedIndex() != 2 ? w.getDocument().getCursor(1) : 0; i < w.getDocument().data.size();) {
                        if (w.getDocument().data.elementAt(i).toString().toLowerCase().indexOf(tfEdit1.getString().toLowerCase()) > -1) {
                            w.getDocument().data.setElementAt(new String(Utils.replace(w.getDocument().data.elementAt(i).toString(), tfEdit1.getString(), tfEdit2.getString())), i);
                        }
                        if (cgSearchVar.getSelectedIndex() == 1) {
                            i--;
                        } else {
                            i++;
                        }
                    }

                    showMenu(-1);
                    break;
                }

                case 8: // '\b'
                {
                    w.getDocument().data.setElementAt("", w.getDocument().getCursor(1));
                    w.getDocument().setCursor(0, w.getDocument().getCursor(1));
                    w.getDocument().Insert(tbLineEditor.getString());
                    showMenu(-1);
                    break;
                }

                case 9: // '\t'
                {
                    int n = Integer.parseInt(tfEdit1.getString()) - 1;
                    if (n >= 0 && n < w.getDocument().data.size()) {
                        w.getDocument().setCursor(0, n);
                    }
                    showMenu(-1);
                    break;
                }

                case 10: // '\n'
                {
                    Database.addTemplate(tfEdit1.getString(), tfEdit2.getString());
                    Database.saveTemplates();
                    showMenu(4);
                    break;
                }

                case 11: // '\013'
                {
                    if (mainMenu.getSelectedIndex() > -1) {
                        Database.editTemplate(mainMenu.getSelectedIndex(), tfEdit1.getString(), tfEdit2.getString());
                    }
                    Database.saveTemplates();
                    showMenu(4);
                    break;
                }
            }
            tfEdit1 = null;
            tfEdit2 = null;
        } else if (c == cmdSave) {
            switch (menu) {
                case 14: // '\016'
                    Database.linenum = cgInterface.isSelected(0);
                    Database.rls = cgInterface.isSelected(1);
                    Database.fontFace = cgFontFace.getSelectedIndex();
                    Database.fontSize = cgFontFace.getSelectedIndex();
                    break;

                case 15: // '\017'
                    Database.cyrLayout = cgLayout.getSelectedIndex();
                    Database.template = tfDocTemplate.getString();
                    Database.templLine = Integer.parseInt(tfDocLine.getString());
                    break;
            }
            showMenu(5);
        } else if (c == cmdAddTemplate || c == cmdEditTemplate) {
            menu = c != cmdAddTemplate ? 11 : 10;
            Form fReplace = new Form(c != cmdAddTemplate ? "\u7F16\u8F91\u6A21\u7248" : "\u65B0\u5EFA\u6A21\u7248");
            try {
                tfEdit1 = new TextField("\u6A21\u7248\u5185\u5BB9", c != cmdAddTemplate ? Database.getTemplateStr(mainMenu.getSelectedIndex()) : "", 100, 0);
                tfEdit2 = new TextField("\u5149\u6807\u5DE6\u79FBn\u4E2A\u5B57\u7B26", c != cmdAddTemplate ? Database.getTemplateInt(mainMenu.getSelectedIndex()) : "0", 5, 2);
                fReplace.append(tfEdit1);
                fReplace.append(tfEdit2);
                fReplace.addCommand(cmdOk);
            } catch (Exception e) {
                fReplace.append("\u6A21\u7248\u4E3A\u7A7A");
            }
            fReplace.addCommand(paste);
            fReplace.addCommand(cmdBack);
            fReplace.setCommandListener(this);
            display.setCurrent(fReplace);
        } else if (c == cmdDelTemplate && mainMenu.getSelectedIndex() > -1) {
            Database.delTemplate(mainMenu.getSelectedIndex());
            Database.saveTemplates();
            showMenu(4);
        }
    }
}
