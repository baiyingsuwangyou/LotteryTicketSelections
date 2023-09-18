import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Pattern;


public class LotteryTicketSelections extends Frame {
    //窗口位置和长宽
    private int X = 100, Y = 100, WIDTH = 1200, HEIGHT = 600;

    //总号码
    private java.util.List<int[]> arrayList = new ArrayList<int[]>();

    //临时存储变量
    private int temp;

    //红蓝按钮按键情况
    private boolean[] btn_red_arr = new boolean[33];
    private boolean[] btn_blue_arr = new boolean[16];

    //GridBag布局
    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    //组件
    private Panel p0 = new Panel();
    private Label l0 = new Label("彩票购买系统", Label.CENTER);
    private Button b00 = new Button("人选");
    private Button b01 = new Button("机选");

    private Panel p = new Panel();

    private Label label_red = new Label("请选择需要机选的红球复式或单式数:", Label.RIGHT);
    private Choice choice_red = new Choice();
    private Button btn_red_select = new Button("机选红色");
    private Button btn_red_clearall = new Button("清空红色");
    private Button[] btns_red = new Button[33];

    private Label label_blue = new Label("请选择需要机选的蓝球复式或单式数:", Label.RIGHT);
    private Choice choice_blue = new Choice();
    private Button btn_blue_select = new Button("机选蓝色");
    private Button btn_blue_clearall = new Button("清空蓝色");
    private Button[] btns_blue = new Button[16];

    private Label label1 = new Label("请输入需要机选的股数:", Label.RIGHT);
    private TextField tf = new TextField();
    private Button btn_sure = new Button("确定");
    private Label tips = new Label("请操作", Label.CENTER);

    private List list_all = new List();
    private Label label = new Label("双击彩票删除对应彩票", Label.CENTER);

    private Label label_mutiple = new Label("倍率", Label.CENTER);
    private Choice choice_mutiple = new Choice();
    private Button btn_add = new Button("添加");
    private Button btn_figure = new Button("结算");

    private Dialog dialog = new Dialog(this, "test");
    private List list_d = new List();

    Random rd = new Random();

    public LotteryTicketSelections() {
        //窗体设置
        this.setTitle("test");
        this.setBounds(X, Y, WIDTH, HEIGHT);
//        this.setResizable(false);
        this.setLayout(null);
        //给窗体添加一个关闭的监听器
        this.addWindowListener(new mywindowclose());

        //组件设置
        //
        p0.setBounds(10, 40, 1180, 525);
        p0.setLayout(null);
        this.add(p0);
        //
        l0.setFont(new Font("华文新魏", Font.BOLD, 96));
        l0.setForeground(Color.blue);
        l0.setBounds(250, 100, 680, 100);
        p0.add(l0);
        //
        b00.setBounds(250, 350, 200, 100);
        p0.add(b00);
        b00.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p0.setVisible(false);
                p.setVisible(true);
            }
        });
        //
        b01.setBounds(730, 350, 200, 100);
        p0.add(b01);
        b01.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p0.setVisible(false);
                p.setVisible(true);
            }
        });

        //
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        //
        p.setBounds(10, 40, 1180, 525);
        p.setLayout(gbl);
        p.setVisible(false);
        this.add(p);

        //
        locate_Components(label_red, 10, 0, 12, 2);
        label_red.setFont(new Font("华文新魏", Font.PLAIN, 18));
        //
        locate_Components(choice_red, 22, 0, 4, 2);
        for (int i = 6; i <= 20; i++)
            choice_red.add(String.valueOf(i));
        //
        locate_Components(btn_red_select, 26, 0, 4, 2);
        btn_red_select.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按按钮后，机选红色号码
                int red_num = choice_red.getSelectedIndex() + 6;

                Arrays.fill(btn_red_arr, false);
                for (Button button : btns_red) button.setBackground(Color.white);

                int[] arr_temp = new int[red_num];
                int count = 0;
                while (count < red_num) {
                    int temp = rd.nextInt(33) + 1;
                    boolean flag = false;
                    for (int i = 0; i < count; i++)
                        if (arr_temp[i] == temp) {
                            flag = true;
                            break;
                        }
                    if (flag)
                        continue;
                    arr_temp[count++] = temp;

                    btn_red_arr[temp - 1] = true;
                    btns_red[temp - 1].setBackground(Color.red);
                }
            }
        });
        //
        locate_Components(btn_red_clearall, 30, 0, 4, 2);
        btn_red_clearall.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按按钮后，清空已选红色
                Arrays.fill(btn_red_arr, false);
                for (Button button : btns_red) button.setBackground(Color.white);
            }
        });
        //
        for (int i = 0; i < 33; i++) {
            btns_red[i] = new Button(String.valueOf(i + 1));
            if (i < 17)
                locate_Components(btns_red[i], 2 * i, 2, 2, 2);
            else
                locate_Components(btns_red[i], 2 * (i - 17), 4, 2, 2);
            btns_red[i].setBackground(Color.white);
            int finalI = i;
            btns_red[i].addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!btn_red_arr[finalI]) {
                        btns_red[finalI].setBackground(Color.red);
                        btn_red_arr[finalI] = true;
                    } else {
                        btns_red[finalI].setBackground(Color.white);
                        btn_red_arr[finalI] = false;
                    }
                }
            });
        }

        //
        locate_Components(label_blue, 10, 6, 12, 2);
        label_blue.setFont(new Font("华文新魏", Font.PLAIN, 18));
        //
        locate_Components(choice_blue, 22, 6, 4, 2);
        for (int i = 1; i <= 16; i++)
            choice_blue.add(String.valueOf(i));
        //
        locate_Components(btn_blue_select, 26, 6, 4, 2);
        btn_blue_select.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按按钮后，机选蓝色号码
                int blue_num = choice_blue.getSelectedIndex() + 1;

                Arrays.fill(btn_blue_arr, false);
                for (Button button : btns_blue) button.setBackground(Color.white);

                int[] arr_temp = new int[blue_num];
                int count = 0;
                while (count < blue_num) {
                    int temp = rd.nextInt(16) + 1;
                    boolean flag = false;
                    for (int i = 0; i < count; i++)
                        if (arr_temp[i] == temp) {
                            flag = true;
                            break;
                        }
                    if (flag)
                        continue;
                    arr_temp[count++] = temp;

                    btn_blue_arr[temp - 1] = true;
                    btns_blue[temp - 1].setBackground(Color.cyan);
                }
            }
        });
        //
        locate_Components(btn_blue_clearall, 30, 6, 4, 2);
        btn_blue_clearall.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按按钮后，清空已选蓝色
                Arrays.fill(btn_blue_arr, false);
                for (Button button : btns_blue) button.setBackground(Color.white);
            }
        });
        //
        for (int i = 0; i < 16; i++) {
            btns_blue[i] = new Button(String.valueOf(i + 1));
            locate_Components(btns_blue[i], i * 2, 8, 2, 2);
            btns_blue[i].setBackground(Color.white);
            int finalI = i;
            btns_blue[i].addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!btn_blue_arr[finalI]) {
                        btns_blue[finalI].setBackground(Color.cyan);
                        btn_blue_arr[finalI] = true;
                    } else {
                        btns_blue[finalI].setBackground(Color.white);
                        btn_blue_arr[finalI] = false;
                    }
                }
            });
        }

        //
        locate_Components(label1, 0, 11, 8, 2);
        label1.setFont(new Font("华文新魏", Font.PLAIN, 18));
        //
        locate_Components(tf, 8, 11, 2, 2);
        tf.setFont(new Font("华文新魏", Font.PLAIN, 18));
        //
        locate_Components(btn_sure, 10, 11, 4, 2);
        btn_sure.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = tf.getText();
                Pattern pattern = Pattern.compile("^[\\d]*$");
                if (pattern.matcher(s).matches()) {
                    //获取数字
                    int n = Integer.parseInt(s);
                    //清空显示组件
                    list_all.removeAll();
                    //随机生成并添加到总号码列表
                    add_random_array(n);
                    //显示arrayList所有内容
                    addList(list_all);
                    //提示
                    tips.setText("当前总股数:" + arrayList.size());
                    tf.setText("");
                } else {
                    //提示
                    tips.setText("请输入数字!");
                    tf.setText("");
                }
            }
        });
        //
        locate_Components(tips, 0, 13, 20, 2);
        tips.setFont(new Font("华文新魏", Font.PLAIN, 18));
        tips.setBackground(Color.LIGHT_GRAY);

        //
        locate_Components(list_all, 20, 10, 10, 4);
        list_all.setFont(new Font("华文新魏", Font.PLAIN, 16));
        list_all.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取双击的序列
                int n = list_all.getSelectedIndex();
                //删去
                list_all.remove(n);
                arrayList.remove(n);
                //提示
                tips.setText("当前总股数:" + arrayList.size());
            }
        });
        //
        locate_Components(label, 20, 14, 10, 1);
        label.setFont(new Font("华文新魏", Font.PLAIN, 18));

        //
        locate_Components(label_mutiple, 30, 10, 4, 1);
        label_mutiple.setFont(new Font("华文新魏", Font.PLAIN, 18));
        label_mutiple.setBackground(Color.lightGray);
        //
        locate_Components(choice_mutiple, 30, 11, 4, 1);
        choice_mutiple.setFont(new Font("华文新魏", Font.BOLD, 16));
        for (int i = 1; i < 6; i++)
            choice_mutiple.add("x" + String.valueOf(i));
        //
        locate_Components(btn_add, 30, 13, 2, 2);
        btn_add.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按按钮后，添加已选号码数
                int[] reds = new int[33];
                int[] blues = new int[16];
                int red = 0, blue = 0;
                for (int i = 0; i < btn_red_arr.length; i++) {
                    if (btn_red_arr[i])
                        reds[red++] = i + 1;
                }
                for (int i = 0; i < btn_blue_arr.length; i++) {
                    if (btn_blue_arr[i])
                        blues[blue++] = i + 1;
                }
                if (red > 5 && blue > 0) {
                    //清空显示组件
                    list_all.removeAll();
                    //所有组合
                    for (int i = 0; i < blue; i++) {
                        temp = blues[i];
                        int[] arr1 = Arrays.copyOf(reds, red);
                        select_n(arr1, 6, 0);
                    }
                    //显示arrayList所有内容
                    addList(list_all);
                    //提示
                    tips.setText("当前总股数:" + arrayList.size());
                } else {
                    //错误提示
                    tips.setText("请至少选择6个红球，1个篮球！");
                }
            }
        });
        //
        locate_Components(btn_figure, 32, 13, 2, 2);
        btn_figure.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //按按钮后，弹出详情及总金额
                int n = choice_mutiple.getSelectedIndex() + 1;
                dialog.setVisible(true);
                list_d.removeAll();
                list_d.add("总股数:" + arrayList.size() + "       " + n + "倍");
                list_d.add("总金额:" + arrayList.size() * 2 * n);
                addList(list_d);
            }
        });

        //
        dialog.setBounds(X + 50, Y + 50, 600, 400);
        dialog.addWindowListener(new mywindowclose());
        //
        list_d.setFont(new Font("华文新魏", Font.BOLD, 16));
        dialog.add(list_d);

        this.setVisible(true);
    }

    //栈stack
    public static Stack<Integer> stack = new Stack<Integer>();

    public void select_n(int[] arr1, int n, int index) {
        if (n == stack.size()) {
            int[] arr_temp = new int[7];
            for (int i = 0; i < 6; i++)
                arr_temp[i] = stack.get(i);
            arr_temp[6] = temp;
            if (!is_exist(arr_temp))
                arrayList.add(arr_temp.clone());
        } else
            for (int i = index; i < arr1.length; i++) {
                if (!stack.contains(arr1[i])) {
                    stack.add(arr1[i]);
                    select_n(arr1, n, i + 1);
                    stack.pop();
                }
            }
    }

    public boolean is_exist(int[] arr1) {
        boolean flag = false;
        for (int[] ints : arrayList) {
            int n = 0;
            for (int j = 0; j < 7; j++)
                if (arr1[j] == ints[j])
                    n++;
            if (n == 7) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public void addList(List l) {
        String[] s_arr = new String[7];
        for (int[] ints : arrayList) {
            for (int j = 0; j < 7; j++) {
                int temp = ints[j];
                if (temp < 10)
                    s_arr[j] = "0" + String.valueOf(temp);
                else
                    s_arr[j] = String.valueOf(temp);
            }
            String s = String.join(" , ", s_arr);
            //添加到显示组件
            l.add(s);
        }
    }

    public void locate_Components(Component component, int gribx, int griby, int gribwidth, int gribheight) {
        gbc.gridx = gribx;
        gbc.gridy = griby;
        gbc.gridwidth = gribwidth;
        gbc.gridheight = gribheight;
        gbl.setConstraints(component, gbc);
        p.add(component);
    }

    public void add_random_array(int n) {
        int count = 0;
        while (count < n) {
            int j = 0;
            int[] arr_temp = new int[7];
            while (j < 6) {
                arr_temp[j] = rd.nextInt(33) + 1;
                boolean flag = false;
                for (int k = 0; k < j; k++)
                    if (arr_temp[k] == arr_temp[j]) {
                        flag = true;
                        break;
                    }
                if (flag)
                    continue;
                j++;
            }
            arr_temp[6] = rd.nextInt(16) + 1;
            maopao_sort(arr_temp);
            if (!is_exist(arr_temp)) {
                arrayList.add(arr_temp.clone());
                count++;
            }
        }
    }

    public void maopao_sort(int[] arr1) {
        for (int i = 0; i < arr1.length - 3; i++)
            for (int j = 0; j < arr1.length - 3 - i; j++)
                if (arr1[j] > arr1[j + 1]) {
                    int temp = arr1[j];
                    arr1[j] = arr1[j + 1];
                    arr1[j + 1] = temp;
                }
    }

    class mywindowclose extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            // 弹出确认框
            // 定义变量，保存确认框的保存值
            int result = JOptionPane.showConfirmDialog(null, "您确定要退出吗？", "退出提示", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            // 判断用户的选择
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);// 正常结束
            }
        }
    }

    public static void main(String[] args) {
        LotteryTicketSelections l = new LotteryTicketSelections();
    }
}

