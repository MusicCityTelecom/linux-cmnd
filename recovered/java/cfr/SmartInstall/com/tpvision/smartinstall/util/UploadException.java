/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

public class UploadException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final ExceptionType type;

    public UploadException(ExceptionType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return this.type.msg;
    }

    public static enum ExceptionType {
        InvalidUploadFileSize("Zip file content null,please check the zip file"),
        InvalidZipfileName("Unable to Upload: zipfile name may have special charater other than -_.,"),
        MultiplePfInZipFile("Multiple platforms detected in the Zip file: Please upload only one Platform at a time and make sure folder in zip file is named exactly as the TV has output. Note! If you are uploading files for a 2013 MediaSuite (xxHFL5008) or Signature (xxHFL7x08) from a SmartInstall server please use the Q554B-2K13PSMS and rename it to Q554B folder."),
        InvalidCloneFolderName("Folder name in zip is not a recognized clone name. Make sure the name of the folder is not changed!"),
        InvalidWelcomeLogoResolution("Welcome logo images should have resolution of 1280 X 720 are allowed"),
        InvalidWelcomeLogoSize("Welcome logo size should not exceeds 2 MB"),
        InvalidWelcomeLogoFormaterPNG("Welcome logo only png images are allowed"),
        InvalidWelcomeLogoFormaterJPG("Welcome logo only jpg images are allowed"),
        HotelInfoImageExcedsTotalCount("Hotel Info image exceds 30, unable to upload."),
        WelcomeLogoNotFound("WelcomeLogo or WelcomeApp not found in the zip file"),
        NOT_ENGOUTH_DISK_SPACE("Failed: disk full for uploading"),
        UploadFileIsNull("upload clone package is null"),
        InvalidZipFile("Failed : the Zip file is broken, can't be opened by 7-zip."),
        EmptyZipFile("Zip file content null,please check the zip file"),
        SaveDataToDbFail("FAILED : Unable to upload clone, some of the tags are missing in channel table xml"),
        ClonePathIsNull("FAILED : clone path is null, please check package"),
        CloneVersionNotCompatible("FAILED : the clone data version not compatible with folder name"),
        ClonePlatformIsNull("FAILED : can't get platform data from clone package"),
        UploadTvSettingFail("upload tv setting clone item failure, please check upload zip data."),
        UploadChannelsFail("upload channels clone item failure, please check upload zip data."),
        UploadAppsFail("upload android apps clone item failure, please check upload zip data."),
        UploadBannersFail("upload bannder clone item failure, please check upload zip data."),
        UploadWelcomeFail("upload welcome log clone item failure, please check upload zip data."),
        UploadUIFail("upload UI clone item failure, please check upload zip data."),
        UploadBannerZipInvalid("Upload failed, Banner folder is missing in uploaded archive!"),
        UPLOAD_SCHEDULES_FAIL("upload schedule clone item failure, please check upload zip data.");

        private String msg;

        private ExceptionType(String msg) {
            this.msg = msg;
        }
    }
}

