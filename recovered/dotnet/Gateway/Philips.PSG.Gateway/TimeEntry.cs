using System;
using System.ComponentModel;
using System.Media;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

public class TimeEntry : MaskedTextBox
{
	private IContainer components;

	public TimeSpan Value
	{
		get
		{
			string text = Text;
			if (text.Equals("24:00"))
			{
				return new TimeSpan(1, 0, 0, 0);
			}
			try
			{
				return TimeSpan.Parse(text);
			}
			catch (Exception)
			{
				return TimeSpan.Zero;
			}
		}
		set
		{
			if (value.Equals(new TimeSpan(1, 0, 0, 0)))
			{
				Text = "24:00";
			}
			else
			{
				Text = string.Format("{0}:{1}", value.Hours.ToString("D2"), value.Minutes.ToString("D2"));
			}
		}
	}

	public TimeEntry()
	{
		InitializeComponent();
	}

	protected override void OnKeyPress(KeyPressEventArgs eventArgs)
	{
		string text = Text;
		bool flag = false;
		bool flag2 = false;
		switch (base.SelectionStart)
		{
		case 0:
			if (eventArgs.KeyChar > '2')
			{
				flag2 = true;
				flag = true;
			}
			else if (eventArgs.KeyChar == '2' && (text[1] > '4' || (text[1] == '4' && (text[3] > '0' || text[4] > '0'))))
			{
				Text = "24:00";
				base.SelectionStart = 1;
				flag = true;
			}
			break;
		case 1:
			if (text[0] == '2' && eventArgs.KeyChar > '4')
			{
				flag2 = true;
				flag = true;
			}
			else if (text[0] == '2' && eventArgs.KeyChar == '4' && (text[3] > '0' || text[4] > '0'))
			{
				Text = "24:00";
				base.SelectionStart = 2;
				flag = true;
			}
			break;
		case 2:
		case 3:
			if (text[0] == '2' && text[1] == '4' && eventArgs.KeyChar != '0')
			{
				flag2 = true;
				flag = true;
			}
			else if (eventArgs.KeyChar > '5')
			{
				flag2 = true;
				flag = true;
			}
			break;
		case 4:
			if (text[0] == '2' && text[1] == '4' && eventArgs.KeyChar != '0')
			{
				flag2 = true;
				flag = true;
			}
			else if (eventArgs.KeyChar != '0' && eventArgs.KeyChar != '5')
			{
				flag2 = true;
				flag = true;
			}
			break;
		}
		if (flag2)
		{
			SystemSounds.Beep.Play();
		}
		if (flag)
		{
			eventArgs.Handled = true;
		}
		else
		{
			base.OnKeyPress(eventArgs);
		}
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
		base.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
		base.Mask = "00:00";
		base.PromptChar = '0';
		base.ResetOnSpace = false;
		this.ShortcutsEnabled = false;
		this.Text = "00:00";
		base.TextMaskFormat = System.Windows.Forms.MaskFormat.IncludePromptAndLiterals;
		base.ResumeLayout(false);
	}
}
