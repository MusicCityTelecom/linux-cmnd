using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace Philips.PSG.Gateway;

public class ConfigurationDialog : Form
{
	private IContainer components;

	private Button cancelButton;

	private Button okButton;

	private Label buildRootFolderLabel;

	private TextBox buildRootFolderTextBox;

	private Button buildRootFolderBrowseButton;

	private Button workingRootFolderBrowseButton;

	private TextBox workingRootFolderTextBox;

	private Label workingRootFolderLabel;

	private Label constellationLabel;

	private ComboBox constellationComboBox;

	private Label frequencyLabel;

	private NumericUpDown frequencyUpDown;

	private Label frequencyUnitLabel;

	private Label frequencyWarningLabel;

	public ConfigurationDialog()
	{
		InitializeComponent();
		InitializeCustom();
	}

	private void InitializeCustom()
	{
		Text = Resources.DLG_ConfigurationTitle;
		okButton.Text = Resources.BTN_OK;
		cancelButton.Text = Resources.BTN_Cancel;
		buildRootFolderLabel.Text = Resources.DLG_ConfigurationBuildRootFolderLabel;
		buildRootFolderBrowseButton.Text = Resources.BTN_Browse;
		workingRootFolderLabel.Text = Resources.DLG_ConfigurationWorkingRootFolderLabel;
		workingRootFolderBrowseButton.Text = Resources.BTN_Browse;
		frequencyLabel.Text = Resources.DLG_ConfigurationFrequencyLabel;
		frequencyUnitLabel.Text = Resources.DLG_ConfigurationFrequencyUnit;
		frequencyWarningLabel.Text = string.Format(Resources.DLG_ConfigurationNotDefaultOutputFrequencyLabel, 7060);
		constellationLabel.Text = Resources.DLG_ConfigurationConstellationLabel;
		frequencyUpDown.Minimum = 450m;
		frequencyUpDown.Maximum = 8640m;
		frequencyUpDown.Increment = 8m;
		frequencyWarningLabel.Visible = false;
		constellationComboBox.Items.AddRange(new object[3]
		{
			Config.CONSTELLATION_QPSK_STRING,
			Config.CONSTELLATION_QAM16_STRING,
			Config.CONSTELLATION_QAM64_STRING
		});
	}

	private void DisplayConfigData(Config config)
	{
		buildRootFolderTextBox.Text = config.BuildRootFolder;
		workingRootFolderTextBox.Text = config.WorkingRootFolder;
		frequencyUpDown.Value = config.ModulationFrequency;
		constellationComboBox.SelectedItem = config.ConstellationAsString;
	}

	private bool GatherConfigData(Config config)
	{
		bool result = false;
		string text = buildRootFolderTextBox.Text;
		if (!config.BuildRootFolder.Equals(text))
		{
			config.BuildRootFolder = text;
			result = true;
		}
		text = workingRootFolderTextBox.Text;
		if (!config.WorkingRootFolder.Equals(text))
		{
			config.WorkingRootFolder = text;
			result = true;
		}
		int num = (int)frequencyUpDown.Value;
		if (config.ModulationFrequency != num)
		{
			config.ModulationFrequency = num;
			result = true;
		}
		text = (string)constellationComboBox.SelectedItem;
		if (!config.ConstellationAsString.Equals(text))
		{
			config.ConstellationAsString = text;
			result = true;
		}
		return result;
	}

	public bool ShowDialog(IWin32Window owner, Config config)
	{
		DisplayConfigData(config);
		if (ShowDialog(owner) == DialogResult.OK)
		{
			return GatherConfigData(config);
		}
		return false;
	}

	private bool ValidateOutputChannelSetting()
	{
		if (frequencyUpDown.Value == 7060m)
		{
			return true;
		}
		if (MessageBox.Show(Resources.DLG_ConfigurationConfirmOutputChannelMessage, Resources.GLOBAL_AppName, MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
		{
			return true;
		}
		return false;
	}

	private void okButton_Click(object sender, EventArgs eventArgs)
	{
		if (ValidateOutputChannelSetting())
		{
			base.DialogResult = DialogResult.OK;
		}
	}

	private bool AskUserForFolder(ref string folder, string message)
	{
		FolderBrowserDialog folderBrowserDialog = new FolderBrowserDialog();
		folderBrowserDialog.ShowNewFolderButton = false;
		folderBrowserDialog.RootFolder = Environment.SpecialFolder.MyComputer;
		folderBrowserDialog.SelectedPath = folder;
		folderBrowserDialog.Description = message;
		if (folderBrowserDialog.ShowDialog(this) == DialogResult.OK)
		{
			folder = folderBrowserDialog.SelectedPath;
			return true;
		}
		return false;
	}

	private void buildRootFolderBrowseButton_Click(object sender, EventArgs eventArgs)
	{
		string folder = buildRootFolderTextBox.Text;
		if (AskUserForFolder(ref folder, string.Format(Resources.DLG_ConfigurationSelectBuildRootFolderMessage, Settings.DefaultBuildRootFolder)))
		{
			buildRootFolderTextBox.Text = folder;
		}
	}

	private void workingRootFolderBrowseButton_Click(object sender, EventArgs eventArgs)
	{
		string folder = workingRootFolderTextBox.Text;
		if (AskUserForFolder(ref folder, string.Format(Resources.DLG_ConfigurationSelectWorkingRootFolderMessage, Settings.DefaultWorkingRootFolder)))
		{
			workingRootFolderTextBox.Text = folder;
		}
	}

	private void frequencyUpDown_ValueChanged(object sender, EventArgs e)
	{
		int num = (int)frequencyUpDown.Value;
		frequencyWarningLabel.Visible = num != 7060;
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
		this.cancelButton = new System.Windows.Forms.Button();
		this.okButton = new System.Windows.Forms.Button();
		this.buildRootFolderLabel = new System.Windows.Forms.Label();
		this.buildRootFolderTextBox = new System.Windows.Forms.TextBox();
		this.buildRootFolderBrowseButton = new System.Windows.Forms.Button();
		this.workingRootFolderBrowseButton = new System.Windows.Forms.Button();
		this.workingRootFolderTextBox = new System.Windows.Forms.TextBox();
		this.workingRootFolderLabel = new System.Windows.Forms.Label();
		this.constellationLabel = new System.Windows.Forms.Label();
		this.constellationComboBox = new System.Windows.Forms.ComboBox();
		this.frequencyLabel = new System.Windows.Forms.Label();
		this.frequencyUpDown = new System.Windows.Forms.NumericUpDown();
		this.frequencyUnitLabel = new System.Windows.Forms.Label();
		this.frequencyWarningLabel = new System.Windows.Forms.Label();
		((System.ComponentModel.ISupportInitialize)this.frequencyUpDown).BeginInit();
		base.SuspendLayout();
		this.cancelButton.Anchor = System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right;
		this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
		this.cancelButton.Location = new System.Drawing.Point(228, 213);
		this.cancelButton.Name = "cancelButton";
		this.cancelButton.Size = new System.Drawing.Size(75, 23);
		this.cancelButton.TabIndex = 0;
		this.cancelButton.Text = "Cancel";
		this.cancelButton.UseVisualStyleBackColor = true;
		this.okButton.Anchor = System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right;
		this.okButton.Location = new System.Drawing.Point(147, 213);
		this.okButton.Name = "okButton";
		this.okButton.Size = new System.Drawing.Size(75, 23);
		this.okButton.TabIndex = 1;
		this.okButton.Text = "OK";
		this.okButton.UseVisualStyleBackColor = true;
		this.okButton.Click += new System.EventHandler(okButton_Click);
		this.buildRootFolderLabel.AutoSize = true;
		this.buildRootFolderLabel.Location = new System.Drawing.Point(12, 17);
		this.buildRootFolderLabel.Name = "buildRootFolderLabel";
		this.buildRootFolderLabel.Size = new System.Drawing.Size(82, 13);
		this.buildRootFolderLabel.TabIndex = 2;
		this.buildRootFolderLabel.Text = "BuildRootFolder";
		this.buildRootFolderTextBox.BackColor = System.Drawing.Color.White;
		this.buildRootFolderTextBox.Location = new System.Drawing.Point(12, 35);
		this.buildRootFolderTextBox.Name = "buildRootFolderTextBox";
		this.buildRootFolderTextBox.ReadOnly = true;
		this.buildRootFolderTextBox.Size = new System.Drawing.Size(209, 20);
		this.buildRootFolderTextBox.TabIndex = 3;
		this.buildRootFolderBrowseButton.Location = new System.Drawing.Point(228, 33);
		this.buildRootFolderBrowseButton.Name = "buildRootFolderBrowseButton";
		this.buildRootFolderBrowseButton.Size = new System.Drawing.Size(75, 23);
		this.buildRootFolderBrowseButton.TabIndex = 4;
		this.buildRootFolderBrowseButton.Text = "Browse";
		this.buildRootFolderBrowseButton.UseVisualStyleBackColor = true;
		this.buildRootFolderBrowseButton.Click += new System.EventHandler(buildRootFolderBrowseButton_Click);
		this.workingRootFolderBrowseButton.Location = new System.Drawing.Point(228, 83);
		this.workingRootFolderBrowseButton.Name = "workingRootFolderBrowseButton";
		this.workingRootFolderBrowseButton.Size = new System.Drawing.Size(75, 23);
		this.workingRootFolderBrowseButton.TabIndex = 7;
		this.workingRootFolderBrowseButton.Text = "Browse";
		this.workingRootFolderBrowseButton.UseVisualStyleBackColor = true;
		this.workingRootFolderBrowseButton.Click += new System.EventHandler(workingRootFolderBrowseButton_Click);
		this.workingRootFolderTextBox.BackColor = System.Drawing.Color.White;
		this.workingRootFolderTextBox.Location = new System.Drawing.Point(12, 85);
		this.workingRootFolderTextBox.Name = "workingRootFolderTextBox";
		this.workingRootFolderTextBox.ReadOnly = true;
		this.workingRootFolderTextBox.Size = new System.Drawing.Size(209, 20);
		this.workingRootFolderTextBox.TabIndex = 6;
		this.workingRootFolderLabel.AutoSize = true;
		this.workingRootFolderLabel.Location = new System.Drawing.Point(12, 67);
		this.workingRootFolderLabel.Name = "workingRootFolderLabel";
		this.workingRootFolderLabel.Size = new System.Drawing.Size(99, 13);
		this.workingRootFolderLabel.TabIndex = 5;
		this.workingRootFolderLabel.Text = "WorkingRootFolder";
		this.constellationLabel.Location = new System.Drawing.Point(12, 169);
		this.constellationLabel.Name = "constellationLabel";
		this.constellationLabel.Size = new System.Drawing.Size(85, 13);
		this.constellationLabel.TabIndex = 11;
		this.constellationLabel.Text = "Constellation";
		this.constellationLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
		this.constellationComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
		this.constellationComboBox.FormattingEnabled = true;
		this.constellationComboBox.Location = new System.Drawing.Point(105, 165);
		this.constellationComboBox.Name = "constellationComboBox";
		this.constellationComboBox.Size = new System.Drawing.Size(90, 21);
		this.constellationComboBox.TabIndex = 12;
		this.frequencyLabel.Location = new System.Drawing.Point(12, 128);
		this.frequencyLabel.Name = "frequencyLabel";
		this.frequencyLabel.Size = new System.Drawing.Size(85, 13);
		this.frequencyLabel.TabIndex = 13;
		this.frequencyLabel.Text = "Frequency";
		this.frequencyLabel.TextAlign = System.Drawing.ContentAlignment.TopRight;
		this.frequencyUpDown.BackColor = System.Drawing.Color.White;
		this.frequencyUpDown.Location = new System.Drawing.Point(105, 128);
		this.frequencyUpDown.Name = "frequencyUpDown";
		this.frequencyUpDown.ReadOnly = true;
		this.frequencyUpDown.Size = new System.Drawing.Size(49, 20);
		this.frequencyUpDown.TabIndex = 15;
		this.frequencyUpDown.ValueChanged += new System.EventHandler(frequencyUpDown_ValueChanged);
		this.frequencyUnitLabel.AutoSize = true;
		this.frequencyUnitLabel.Location = new System.Drawing.Point(158, 131);
		this.frequencyUnitLabel.Name = "frequencyUnitLabel";
		this.frequencyUnitLabel.Size = new System.Drawing.Size(29, 13);
		this.frequencyUnitLabel.TabIndex = 16;
		this.frequencyUnitLabel.Text = "MHz";
		this.frequencyWarningLabel.ForeColor = System.Drawing.Color.Red;
		this.frequencyWarningLabel.Location = new System.Drawing.Point(187, 131);
		this.frequencyWarningLabel.Name = "frequencyWarningLabel";
		this.frequencyWarningLabel.Size = new System.Drawing.Size(129, 13);
		this.frequencyWarningLabel.TabIndex = 17;
		this.frequencyWarningLabel.Text = "Frequency Warning";
		base.AutoScaleMode = System.Windows.Forms.AutoScaleMode.None;
		base.CancelButton = this.cancelButton;
		base.ClientSize = new System.Drawing.Size(315, 260);
		base.Controls.Add(this.frequencyWarningLabel);
		base.Controls.Add(this.frequencyUnitLabel);
		base.Controls.Add(this.frequencyUpDown);
		base.Controls.Add(this.frequencyLabel);
		base.Controls.Add(this.constellationComboBox);
		base.Controls.Add(this.constellationLabel);
		base.Controls.Add(this.workingRootFolderBrowseButton);
		base.Controls.Add(this.workingRootFolderTextBox);
		base.Controls.Add(this.workingRootFolderLabel);
		base.Controls.Add(this.buildRootFolderBrowseButton);
		base.Controls.Add(this.buildRootFolderTextBox);
		base.Controls.Add(this.buildRootFolderLabel);
		base.Controls.Add(this.okButton);
		base.Controls.Add(this.cancelButton);
		base.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
		base.MaximizeBox = false;
		base.MinimizeBox = false;
		base.Name = "ConfigurationDialog";
		base.ShowIcon = false;
		base.ShowInTaskbar = false;
		base.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
		this.Text = "ConfigurationDialog";
		((System.ComponentModel.ISupportInitialize)this.frequencyUpDown).EndInit();
		base.ResumeLayout(false);
		base.PerformLayout();
	}
}
