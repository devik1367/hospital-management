package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReferralStatus {

	private int referralId;
	private int facilityId;
	private int medicalStaffId;

	private ArrayList<ReferralReason> reasons;

	public int getReferralId() {
		return referralId;
	}

	public void setReferralId(int referralId) {
		this.referralId = referralId;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public int getMedicalStaffId() {
		return medicalStaffId;
	}

	public void setMedicalStaffId(int medicalStaffId) {
		this.medicalStaffId = medicalStaffId;
	}

	public void addReferralReason(ReferralReason reason) {
		if (reasons == null) {
			reasons = new ArrayList<ReferralReason>();
		}
		reasons.add(reason);
	}

	public ArrayList<ReferralReason> getReasons(Connection conn) throws SQLException {
		if (reasons == null) {
			reasons = new ArrayList<ReferralReason>();
			String sql = "SELECT * FROM referral_reason WHERE referral_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, referralId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				if (reasons == null) {
					reasons = new ArrayList<ReferralReason>();
				}
				ReferralReason reason = new ReferralReason();
				reason.load(rs);
				reasons.add(reason);
			}
		}
		return reasons;
	}

	public void load(ResultSet rs) throws SQLException {
		referralId = rs.getInt("referral_id");
		facilityId = rs.getInt("facility_id");
		medicalStaffId = rs.getInt("medical_staff_id");
	}

	public void insert(Connection conn) throws SQLException {
		String sql = "INSERT INTO referral_status(facility_id,medical_staff_id) VALUES (?,?)";
		String[] primaryKey = { "referral_id" };
		PreparedStatement ps = conn.prepareStatement(sql, primaryKey);
		ps.setInt(1, facilityId);
		ps.setInt(2, medicalStaffId);

		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			referralId = rs.getInt(1);
		}
	}

}
