using System;
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Media;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

public class EditEventDialog : Form
{
	private IContainer components;

	private Button okButton;

	private Button cancelButton;

	private Label tvModelLabel;

	private Label playbackFileLabel;

	private TextBox tvModelTextBox;

	private TextBox playbackFileTextBox;

	private Label startTimeLabel;

	private Label endTimeLabel;

	private TimeEntry startTimeEntry;

	private TimeEntry endTimeEntry;

	private Label validationErrorLabel;

	public EditEventDialog()
	{
		InitializeComponent();
		InitializeCustom();
	}

	private void InitializeCustom()
	{
		Text = Resources.DLG_EditEventTitle;
		okButton.Text = Resources.BTN_OK;
		cancelButton.Text = Resources.BTN_Cancel;
		tvModelLabel.Text = Resources.DLG_EditTvModelLabel;
		playbackFileLabel.Text = Resources.DLG_EditPlaybackFileLabel;
		startTimeLabel.Text = Resources.DLG_EditStartTimeLabel;
		endTimeLabel.Text = Resources.DLG_EditEndTimeLabel;
	}

	private void DisplayEventData(ScheduleEvent scheduleEvent)
	{
		tvModelTextBox.Text = scheduleEvent.TargetTvModel;
		playbackFileTextBox.Text = Path.GetFileName(scheduleEvent.TransportStreamFile);
		startTimeEntry.Value = scheduleEvent.StartTime;
		endTimeEntry.Value = scheduleEvent.EndTime;
	}

	private bool GatherEventData(ScheduleEvent scheduleEvent)
	{
		bool result = false;
		TimeSpan value = startTimeEntry.Value;
		if (!scheduleEvent.StartTime.Equals(value))
		{
			scheduleEvent.StartTime = value;
			result = true;
		}
		value = endTimeEntry.Value;
		if (!scheduleEvent.EndTime.Equals(value))
		{
			scheduleEvent.EndTime = value;
			result = true;
		}
		return result;
	}

	private bool ValidateEdits()
	{
		TimeSpan value = startTimeEntry.Value;
		TimeSpan value2 = endTimeEntry.Value;
		if (value.CompareTo(value2) >= 0)
		{
			SystemSounds.Beep.Play();
			validationErrorLabel.Text = Resources.DLG_EditInvalidTimesMessage;
			return false;
		}
		return true;
	}

	public bool ShowDialog(IWin32Window owner, ScheduleEvent scheduleEvent)
	{
		validationErrorLabel.Text = string.Empty;
		DisplayEventData(scheduleEvent);
		if (ShowDialog(owner) == DialogResult.OK)
		{
			return GatherEventData(scheduleEvent);
		}
		return false;
	}

	private void okButton_Click(object sender, EventArgs eventArgs)
	{
		if (ValidateEdits())
		{
			base.DialogResult = DialogResult.OK;
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
		this.okButton = new System.Windows.Forms.Button();
		this.cancelButton = new System.Windows.Forms.Button();
		this.tvModelLabel = new System.Windows.Forms.Label();
		this.playbackFileLabel = new System.Windows.Forms.Label();
		this.tvModelTextBox = new System.Windows.Forms.TextBox();
		this.playbackFileTextBox = new System.Windows.Forms.TextBox();
		this.startTimeLabel = new System.Windows.Forms.Label();
		this.endTimeLabel = new System.Windows.Forms.Label();
		this.validationErrorLabel = new System.Windows.Forms.Label();
		this.endTimeEntry = new Philips.PSG.Gateway.TimeEntry();
		this.startTimeEntry = new Philips.PSG.Gateway.TimeEntry();
		base.SuspendLayout();
		this.okButton.Location = new System.Drawing.Point(124, 194);
		this.okButton.Name = "okButton";
		this.okButton.Size = new System.Drawing.Size(75, 23);
		this.okButton.TabIndex = 0;
		this.okButton.Text = "OK";
		this.okButton.UseVisualStyleBackColor = true;
		this.okButton.Click += new System.EventHandler(okButton_Click);
		this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
		this.cancelButton.Location = new System.Drawing.Point(205, 194);
		this.cancelButton.Name = "cancelButton";
		this.cancelButton.Size = new System.Drawing.Size(75, 23);
		this.cancelButton.TabIndex = 1;
		this.cancelButton.Text = "Cancel";
		this.cancelButton.UseVisualStyleBackColor = true;
		this.tvModelLabel.Location = new System.Drawing.Point(12, 30);
		this.tvModelLabel.Name = "tvModelLabel";
		this.tvModelLabel.Size = new System.Drawing.Size(100, 13);
		this.tvModelLabel.TabIndex = 2;
		this.tvModelLabel.Text = "TvModelLabel";
		this.tvModelLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		this.playbackFileLabel.Location = new System.Drawing.Point(12, 60);
		this.playbackFileLabel.Name = "playbackFileLabel";
		this.playbackFileLabel.Size = new System.Drawing.Size(100, 13);
		this.playbackFileLabel.TabIndex = 4;
		this.playbackFileLabel.Text = "PlaybackFileLabel";
		this.playbackFileLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		this.tvModelTextBox.Location = new System.Drawing.Point(120, 27);
		this.tvModelTextBox.Name = "tvModelTextBox";
		this.tvModelTextBox.ReadOnly = true;
		this.tvModelTextBox.Size = new System.Drawing.Size(120, 20);
		this.tvModelTextBox.TabIndex = 6;
		this.playbackFileTextBox.Location = new System.Drawing.Point(120, 57);
		this.playbackFileTextBox.Name = "playbackFileTextBox";
		this.playbackFileTextBox.ReadOnly = true;
		this.playbackFileTextBox.Size = new System.Drawing.Size(120, 20);
		this.playbackFileTextBox.TabIndex = 7;
		this.startTimeLabel.Location = new System.Drawing.Point(12, 90);
		this.startTimeLabel.Name = "startTimeLabel";
		this.startTimeLabel.Size = new System.Drawing.Size(100, 13);
		this.startTimeLabel.TabIndex = 8;
		this.startTimeLabel.Text = "StartTimeLabel";
		this.startTimeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		this.endTimeLabel.Location = new System.Drawing.Point(12, 120);
		this.endTimeLabel.Name = "endTimeLabel";
		this.endTimeLabel.Size = new System.Drawing.Size(100, 13);
		this.endTimeLabel.TabIndex = 9;
		this.endTimeLabel.Text = "EndTimeLabel";
		this.endTimeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		this.validationErrorLabel.ForeColor = System.Drawing.Color.Red;
		this.validationErrorLabel.Location = new System.Drawing.Point(15, 152);
		this.validationErrorLabel.Name = "validationErrorLabel";
		this.validationErrorLabel.Size = new System.Drawing.Size(265, 30);
		this.validationErrorLabel.TabIndex = 12;
		this.validationErrorLabel.Text = "ValidationErrorLabel";
		this.endTimeEntry.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
		this.endTimeEntry.Location = new System.Drawing.Point(120, 117);
		this.endTimeEntry.Mask = "00:00";
		this.endTimeEntry.Name = "endTimeEntry";
		this.endTimeEntry.PromptChar = '0';
		this.endTimeEntry.ResetOnSpace = false;
		this.endTimeEntry.ShortcutsEnabled = false;
		this.endTimeEntry.Size = new System.Drawing.Size(40, 20);
		this.endTimeEntry.TabIndex = 11;
		this.endTimeEntry.TextMaskFormat = System.Windows.Forms.MaskFormat.IncludePromptAndLiterals;
		this.endTimeEntry.Value = System.TimeSpan.Parse("00:00:00");
		this.startTimeEntry.InsertKeyMode = System.Windows.Forms.InsertKeyMode.Overwrite;
		this.startTimeEntry.Location = new System.Drawing.Point(120, 87);
		this.startTimeEntry.Mask = "00:00";
		this.startTimeEntry.Name = "startTimeEntry";
		this.startTimeEntry.PromptChar = '0';
		this.startTimeEntry.ResetOnSpace = false;
		this.startTimeEntry.ShortcutsEnabled = false;
		this.startTimeEntry.Size = new System.Drawing.Size(40, 20);
		this.startTimeEntry.TabIndex = 10;
		this.startTimeEntry.TextMaskFormat = System.Windows.Forms.MaskFormat.IncludePromptAndLiterals;
		this.startTimeEntry.Value = System.TimeSpan.Parse("00:00:00");
		base.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
		base.CancelButton = this.cancelButton;
		base.ClientSize = new System.Drawing.Size(292, 229);
		base.Controls.Add(this.validationErrorLabel);
		base.Controls.Add(this.endTimeEntry);
		base.Controls.Add(this.startTimeEntry);
		base.Controls.Add(this.endTimeLabel);
		base.Controls.Add(this.startTimeLabel);
		base.Controls.Add(this.playbackFileTextBox);
		base.Controls.Add(this.tvModelTextBox);
		base.Controls.Add(this.playbackFileLabel);
		base.Controls.Add(this.tvModelLabel);
		base.Controls.Add(this.cancelButton);
		base.Controls.Add(this.okButton);
		base.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		base.MaximizeBox = false;
		base.MinimizeBox = false;
		base.Name = "EditEventDialog";
		base.ShowIcon = false;
		base.ShowInTaskbar = false;
		base.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
		this.Text = "EditEventDialog";
		base.ResumeLayout(false);
		base.PerformLayout();
	}
}
