package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import classes.Circle;
import classes.Rectangle;
import classes.Rhombus;
import classes.Triangle;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8972845754063080764L;
	JPanel panel;

	public MainWindow() { initUI(); }

	private void initUI() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel shapePanel = new JPanel();
		shapePanel.setLayout(new BoxLayout(shapePanel, BoxLayout.X_AXIS));
		final String[] opts = {"Circle", "Rectangle", "Rhombus", "Triangle"};
		final JComboBox<String> shapeList = new JComboBox<>(opts);
		shapePanel.add(new JLabel("Select a Shape: "));
		shapePanel.add(shapeList);
		panel.add(shapePanel);

		final JPanel areaPanel = new JPanel();
		areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.X_AXIS));
		final JPanel areaP = new JPanel();
		areaP.setLayout(new BoxLayout(areaP, BoxLayout.Y_AXIS));
		areaPanel.add(new JLabel("Calculate Area:      "));
		areaPanel.add(areaP);
		panel.add(areaPanel);

		JPanel perimPanel = new JPanel();
		perimPanel.setLayout(new BoxLayout(perimPanel, BoxLayout.X_AXIS));
		final JPanel perimP = new JPanel();
		perimP.setLayout(new BoxLayout(perimP, BoxLayout.Y_AXIS));
		perimPanel.add(new JLabel("Calculate Perimeter: "));
		perimPanel.add(perimP);
		panel.add(perimPanel);

		JPanel anglePanel = new JPanel();
		anglePanel.setLayout(new BoxLayout(anglePanel, BoxLayout.X_AXIS));
		final JPanel angleP = new JPanel();
		angleP.setLayout(new BoxLayout(angleP, BoxLayout.Y_AXIS));
		anglePanel.add(new JLabel("By Angle (alt):      "));
		anglePanel.add(angleP);
		panel.add(anglePanel);

		setupCircle(areaP, perimP, angleP);

		shapeList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					areaP.removeAll(); perimP.removeAll(); angleP.removeAll();
					String name = (String) shapeList.getSelectedItem();
					if      ("Circle".equals(name))    setupCircle(areaP, perimP, angleP);
					else if ("Rectangle".equals(name)) setupRectangle(areaP, perimP, angleP);
					else if ("Rhombus".equals(name))   setupRhombus(areaP, perimP, angleP);
					else                               setupTriangle(areaP, perimP, angleP);
				}
			}
		});

		add(panel);
		pack();
		setTitle("Shape Calculator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// ── RECTANGLE ───────────────────────────────────────────────────────────
	private void setupRectangle(JPanel areaP, JPanel perimP, JPanel angleP) {
		final JTextField aLen = row(areaP, "Length:"); final JTextField aWid = row(areaP, "Width:");
		solve(areaP, "Solve Area", () -> {
			Rectangle r = new Rectangle(val(aLen), val(aWid));
			return fmt(r.area());
		});

		final JTextField pLen = row(perimP, "Length:"); final JTextField pWid = row(perimP, "Width:");
		solve(perimP, "Solve Perimeter", () -> {
			Rectangle r = new Rectangle(val(pLen), val(pWid));
			return fmt(r.perimeter());
		});
		solve(perimP, "Solve Radii", () -> {
			Rectangle r = new Rectangle(val(pLen), val(pWid));
			double e = r.getRadiusExternal(), i = r.getRadiusInternal();
			return "R = " + fmt(e) + "  |  r = " + (i < 0 ? "не существует" : fmt(i));
		});

		sep(angleP, "По диагонали и углу");
		final JTextField aDiag = row(angleP, "Diagonal d:"); final JTextField aAng = row(angleP, "Angle α (°):");
		solve(angleP, "Solve by Angle", () -> {
			double d = val(aDiag), a = val(aAng);
			if (a <= 0 || a >= 90) return "Угол должен быть 0° < α < 90°";
			Rectangle r = new Rectangle(d, a, true);
			return "length=" + fmt(r.getLength()) + "  width=" + fmt(r.getWidth())
					+ "  S=" + fmt(r.area()) + "  P=" + fmt(r.perimeter());
		});
		refresh(areaP, perimP, angleP);
	}

	// ── RHOMBUS ─────────────────────────────────────────────────────────────
	private void setupRhombus(JPanel areaP, JPanel perimP, JPanel angleP) {
		final JTextField aLen = row(areaP, "Length:"); final JTextField aHgt = row(areaP, "Height:");
		solve(areaP, "Solve Area", () -> fmt(new Rhombus(val(aLen), val(aHgt)).area()));

		final JTextField pLen = row(perimP, "Length:"); final JTextField pHgt = row(perimP, "Height:");
		solve(perimP, "Solve Perimeter", () -> fmt(new Rhombus(val(pLen), val(pHgt)).perimeter()));
		solve(perimP, "Solve Radii", () -> {
			Rhombus r = new Rhombus(val(pLen), val(pHgt));
			double ri = r.getRadiusInternal(), re = r.getRadiusExternal();
			return "r = " + fmt(ri) + "  |  R = " + (re < 0 ? "не существует" : fmt(re));
		});

		sep(angleP, "По стороне и острому углу");
		final JTextField aSide = row(angleP, "Side a:"); final JTextField aAng = row(angleP, "Angle α (°):");
		solve(angleP, "Solve by Angle", () -> {
			double a = val(aSide), ang = val(aAng);
			if (ang <= 0 || ang >= 180) return "Угол должен быть 0° < α < 180°";
			Rhombus r = new Rhombus(a, ang, true);
			return "S=" + fmt(r.area()) + "  P=" + fmt(r.perimeter())
					+ "  r(вписанная)=" + fmt(r.getRadiusInternal());
		});
		refresh(areaP, perimP, angleP);
	}

	// ── TRIANGLE ────────────────────────────────────────────────────────────
	private void setupTriangle(JPanel areaP, JPanel perimP, JPanel angleP) {
		final JTextField aBase = row(areaP, "Base:"); final JTextField aHgt = row(areaP, "Height:");
		solve(areaP, "Solve Area", () -> fmt(new Triangle(val(aBase), val(aHgt), 0, 0, 0).area()));

		final JTextField s1 = row(perimP, "Side 1:"); final JTextField s2 = row(perimP, "Side 2:");
		final JTextField s3 = row(perimP, "Side 3:");
		solve(perimP, "Solve Perimeter", () -> fmt(new Triangle(0, 0, val(s1), val(s2), val(s3)).perimeter()));
		solve(perimP, "Solve Radii", () -> {
			Triangle t = new Triangle(0, 0, val(s1), val(s2), val(s3));
			double ri = t.getRadiusInternal(), re = t.getRadiusExternal();
			if (ri < 0) return "Треугольник с такими сторонами не существует";
			return "r = " + fmt(ri) + "  |  R = " + fmt(re);
		});

		sep(angleP, "По двум сторонам и углу между ними");
		final JTextField aSide1 = row(angleP, "Side a:"); final JTextField aSide2 = row(angleP, "Side b:");
		final JTextField aAng   = row(angleP, "Angle C (°):");
		solve(angleP, "Solve by Angle", () -> {
			double a = val(aSide1), b = val(aSide2), ang = val(aAng);
			if (ang <= 0 || ang >= 180) return "Угол должен быть 0° < C < 180°";
			Triangle t = new Triangle(a, b, ang, true);
			double ri = t.getRadiusInternal(), re = t.getRadiusExternal();
			return "c=" + fmt(t.perimeter()-a-b) + "  S=" + fmt(t.area())
					+ "  P=" + fmt(t.perimeter()) + "  r=" + fmt(ri) + "  R=" + fmt(re);
		});
		refresh(areaP, perimP, angleP);
	}

	// ── CIRCLE ──────────────────────────────────────────────────────────────
	private void setupCircle(JPanel areaP, JPanel perimP, JPanel angleP) {
		final JTextField ar = row(areaP, "Radius:");
		solve(areaP, "Solve Area", () -> fmt(new Circle(val(ar)).area()));
		final JTextField pr = row(perimP, "Radius:");
		solve(perimP, "Solve Perimeter", () -> fmt(new Circle(val(pr)).perimeter()));
		sep(angleP, "Для круга угловой метод неприменим");
		refresh(areaP, perimP, angleP);
	}

	interface Calc { String run(); }

	private JTextField row(JPanel p, String label) {
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
		row.add(new JLabel(label + " "));
		JTextField f = new JTextField(18);
		row.add(f);
		p.add(row);
		return f;
	}

	private void solve(JPanel p, String btnText, Calc calc) {
		JPanel row = new JPanel();
		row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
		JButton btn = new JButton(btnText);
		JTextField res = new JTextField(30);
		res.setEditable(false); res.setBackground(Color.WHITE);
		row.add(btn); row.add(res);
		p.add(row);
		btn.addActionListener((ActionEvent e) -> {
			try { res.setText(calc.run()); }
			catch (NumberFormatException ex) { res.setText("Введите корректные числа!"); }
		});
	}

	private void sep(JPanel p, String text) {
		JLabel lbl = new JLabel("── " + text);
		lbl.setForeground(new Color(100, 100, 180));
		p.add(lbl);
	}

	private void refresh(JPanel... panels) {
		for (JPanel p : panels) { p.revalidate(); p.repaint(); }
		pack();
	}

	private double val(JTextField f) { return Double.parseDouble(f.getText().trim()); }
	private String fmt(double v) { return String.format("%.4f", v); }
}