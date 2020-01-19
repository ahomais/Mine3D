package com.independentdesigners.demo;

public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName(){
        return fileName;
    }
    public String getFileDownloadUri(){
        return fileDownloadUri;
    }
    public String getFileType(){
        return fileType;
    }
    // Getters and Setters (Omitted for brevity)
}