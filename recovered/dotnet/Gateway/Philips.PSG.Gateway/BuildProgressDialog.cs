using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

public class BuildProgressDialog : Form
{
	private delegate void Invoke_SetTvModel(string text);

	private delegate void Invoke_SetAction(string text);

	private delegate void Invoke_SetResult(string text, bool viewLogButtonVisible);

	private IContainer components;

	private Panel buildingControlsBox;

	private Label actionLabel;

	private Label tvModelLabel;

	private ProgressBar progressBar;

	private Panel completedControlsBox;

	private Label resultLabel;

	private Button okButton;

	private Button viewLogButton;

	public BuildProgressDialog()
	{
		InitializeComponent();
		InitializeCustom();
	}

	private void InitializeCustom()
	{
		okButton.Text = Resources.BTN_OK;
		viewLogButton.Text = Resources.BTN_ViewBuildLog;
		Text = Resources.DLG_BuildProgressTitle;
		Reset();
	}

	public void Reset()
	{
		tvModelLabel.Text = string.Empty;
		actionLabel.Text = string.Empty;
		resultLabel.Text = string.Empty;
		SetBuildingDisplay();
	}

	public void SetTvModel(string text)
	{
		if (base.InvokeRequired)
		{
			Invoke(new Invoke_SetTvModel(SetTvModel), text);
		}
		else
		{
			tvModelLabel.Text = text;
			SetBuildingDisplay();
		}
	}

	public void SetAction(string text)
	{
		if (base.InvokeRequired)
		{
			Invoke(new Invoke_SetAction(SetAction), text);
		}
		else
		{
			actionLabel.Text = text;
		}
	}

	public void SetResult(string text, bool viewLogButtonVisible)
	{
		if (base.InvokeRequired)
		{
			Invoke(new Invoke_SetResult(SetResult), text, viewLogButtonVisible);
		}
		else
		{
			resultLabel.Text = text;
			SetCompletedDisplay(viewLogButtonVisible);
		}
	}

	private void SetBuildingDisplay()
	{
		SetDisplay(building: true, viewLogButtonVisible: false);
	}

	private void SetCompletedDisplay(bool viewLogButtonVisible)
	{
		SetDisplay(building: false, viewLogButtonVisible);
	}

	private void SetDisplay(bool building, bool viewLogButtonVisible)
	{
		if (building)
		{
			buildingControlsBox.Visible = true;
			completedControlsBox.Visible = false;
			return;
		}
		completedControlsBox.Visible = true;
		buildingControlsBox.Visible = false;
		viewLogButton.Visible = viewLogButtonVisible;
		if (viewLogButtonVisible)
		{
			viewLogButton.Enabled = File.Exists(ConfigFactory.CurrentConfig.BuildLogFile);
			Close();
		}
		else
		{
			Close();
		}
	}

	private void okButton_Click(object sender, EventArgs eventArgs)
	{
		Close();
	}

	private void viewLogButton_Click(object sender, EventArgs eventArgs)
	{
		try
		{
			Process.Start(ConfigFactory.CurrentConfig.BuildLogFile);
		}
		catch (Exception)
		{
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
		this.buildingControlsBox = new System.Windows.Forms.Panel();
		this.actionLabel = new System.Windows.Forms.Label();
		this.tvModelLabel = new System.Windows.Forms.Label();
		this.progressBar = new System.Windows.Forms.ProgressBar();
		this.completedControlsBox = new System.Windows.Forms.Panel();
		this.viewLogButton = new System.Windows.Forms.Button();
		this.okButton = new System.Windows.Forms.Button();
		this.resultLabel = new System.Windows.Forms.Label();
		this.buildingControlsBox.SuspendLayout();
		this.completedControlsBox.SuspendLayout();
		base.SuspendLayout();
		this.buildingControlsBox.Controls.Add(this.actionLabel);
		this.buildingControlsBox.Controls.Add(this.tvModelLabel);
		this.buildingControlsBox.Controls.Add(this.progressBar);
		this.buildingControlsBox.Location = new System.Drawing.Point(13, 13);
		this.buildingControlsBox.Name = "buildingControlsBox";
		this.buildingControlsBox.Size = new System.Drawing.Size(267, 137);
		this.buildingControlsBox.TabIndex = 0;
		this.actionLabel.AutoSize = true;
		this.actionLabel.Location = new System.Drawing.Point(13, 81);
		this.actionLabel.Name = "actionLabel";
		this.actionLabel.Size = new System.Drawing.Size(37, 13);
		this.actionLabel.TabIndex = 2;
		this.actionLabel.Text = "Action";
		this.tvModelLabel.AutoSize = true;
		this.tvModelLabel.Location = new System.Drawing.Point(13, 13);
		this.tvModelLabel.Name = "tvModelLabel";
		this.tvModelLabel.Size = new System.Drawing.Size(49, 13);
		this.tvModelLabel.TabIndex = 1;
		this.tvModelLabel.Text = "TvModel";
		this.progressBar.Location = new System.Drawing.Point(13, 43);
		this.progressBar.Name = "progressBar";
		this.progressBar.Size = new System.Drawing.Size(241, 23);
		this.progressBar.Style = System.Windows.Forms.ProgressBarStyle.Marquee;
		this.progressBar.TabIndex = 0;
		this.completedControlsBox.Controls.Add(this.viewLogButton);
		this.completedControlsBox.Controls.Add(this.okButton);
		this.completedControlsBox.Controls.Add(this.resultLabel);
		this.completedControlsBox.Location = new System.Drawing.Point(13, 13);
		this.completedControlsBox.Name = "completedControlsBox";
		this.completedControlsBox.Size = new System.Drawing.Size(267, 137);
		this.completedControlsBox.TabIndex = 1;
		this.viewLogButton.Location = new System.Drawing.Point(107, 110);
		this.viewLogButton.Name = "viewLogButton";
		this.viewLogButton.Size = new System.Drawing.Size(75, 23);
		this.viewLogButton.TabIndex = 2;
		this.viewLogButton.Text = "ViewLog";
		this.viewLogButton.UseVisualStyleBackColor = true;
		this.viewLogButton.Click += new System.EventHandler(viewLogButton_Click);
		this.okButton.Location = new System.Drawing.Point(188, 110);
		this.okButton.Name = "okButton";
		this.okButton.Size = new System.Drawing.Size(75, 23);
		this.okButton.TabIndex = 1;
		this.okButton.Text = "OK";
		this.okButton.UseVisualStyleBackColor = true;
		this.okButton.Click += new System.EventHandler(okButton_Click);
		this.resultLabel.Location = new System.Drawing.Point(13, 13);
		this.resultLabel.Name = "resultLabel";
		this.resultLabel.Size = new System.Drawing.Size(240, 81);
		this.resultLabel.TabIndex = 0;
		this.resultLabel.Text = "Result";
		base.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
		base.ClientSize = new System.Drawing.Size(292, 161);
		base.ControlBox = false;
		base.Controls.Add(this.buildingControlsBox);
		base.Controls.Add(this.completedControlsBox);
		base.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		base.MaximizeBox = false;
		base.MinimizeBox = false;
		base.Name = "BuildProgressDialog";
		base.ShowIcon = false;
		base.ShowInTaskbar = false;
		base.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
		this.Text = "BuildProgressDialog";
		this.buildingControlsBox.ResumeLayout(false);
		this.buildingControlsBox.PerformLayout();
		this.completedControlsBox.ResumeLayout(false);
		base.ResumeLayout(false);
	}
}
