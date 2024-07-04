package train.reservation.system.entity;

public class ChangePasswordRequest {

	private String newPassword;
	private String oldPassword;
	private String mailId;

	public ChangePasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangePasswordRequest(String newPassword, String oldPassword, String mailId) {
		super();
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
		this.mailId = mailId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
}
