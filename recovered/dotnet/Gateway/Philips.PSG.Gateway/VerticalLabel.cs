using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

public class VerticalLabel : Label
{
	private IContainer components;

	public VerticalLabel()
	{
		InitializeComponent();
	}

	protected override void OnPaint(PaintEventArgs eventArgs)
	{
		Graphics graphics = eventArgs.Graphics;
		Brush brush = new SolidBrush(ForeColor);
		SizeF sizeF = graphics.MeasureString(Text, Font);
		graphics.TranslateTransform(0f, base.Height);
		graphics.RotateTransform(-90f);
		graphics.DrawString(Text, Font, brush, ((float)base.Height - sizeF.Width) / 2f, 0f);
	}

	protected override void Dispose(bool disposing)
	{
		if (disposing && components != null)
		{
			components.Dispose();
		}
		base.Dispose(disposing);
	}

	private void InitializeComponent()
	{
		base.SuspendLayout();
		base.ResumeLayout(false);
	}
}
