/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package thuchanh1;
import java.util.*;
import java.io.File;

public class HotelService {

    private static Map<String, String> otpStore = new HashMap<>();
    private static Map<String, Long> otpExpireTime = new HashMap<>();
    private static Map<Integer, Integer> feedbackStore = new HashMap<>();

    // ======================
    // Registration
    // ======================
    public static boolean register(String phone) {
        // Kiểm tra số điện thoại không được null
        // Số điện thoại phải bắt đầu bằng 0 và có đúng 10 chữ số
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            return false;
        }

        // Nếu số điện thoại hợp lệ thì gửi OTP
        sendOtp(phone);
        return true;
    }

    private static void sendOtp(String phone) {
        // Tạo mã OTP ngẫu nhiên gồm 6 chữ số
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // Lưu OTP theo số điện thoại
        otpStore.put(phone, otp);

        // OTP hết hạn sau 2 phút
        otpExpireTime.put(phone, System.currentTimeMillis() + 2 * 60 * 1000);

        // In OTP ra màn hình để demo
        System.out.println("OTP của " + phone + " là: " + otp);
    }

    // ======================
    // Login
    // ======================
    public static boolean login(String phone, String otp) {
        if (phone == null || otp == null) 
        { 
            return false;
        }

        if (storedOtp == null || expireTime == null || System.currentTimeMillis() > expireTime) {
            // Tối ưu cấu trúc dữ liệu: Nếu OTP đã hết hạn, dọn dẹp (remove) khỏi Map 
            // để tránh rò rỉ bộ nhớ (Memory Leak) khi vận hành lâu dài.
            if (expireTime != null && System.currentTimeMillis() > expireTime) {
                otpStore.remove(phone);
                otpExpireTime.remove(phone);
            }
            return false;
        }

        // 2. Xác thực OTP
        boolean isSuccess = storedOtp.equals(otp);

        // 3. Tối ưu bảo mật (Rất Quan Trọng): 
        // Sau khi đăng nhập thành công, PHẢI xóa OTP ngay lập tức.
        // Tránh tình trạng kẻ gian lấy được mã và tái sử dụng (Replay Attack) trong vòng 2 phút.
        if (isSuccess) {
            otpStore.remove(phone);
            otpExpireTime.remove(phone);
        }

        return isSuccess;
    }

    // ======================
    // Edit Profile
    // ======================
 public static boolean updateProfile(String name, String location) {
        // Tối ưu logic: Gom điều kiện cho gọn gàng.
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        
        if (location == null || location.trim().isEmpty()) {
            return false;
        }

        // Tối ưu mở rộng: Trong thực tế, bạn nên giới hạn thêm độ dài của chuỗi
        // Ví dụ: if (name.length() > 50) return false;
        
        return true;
    }

// =====================================================

    public static boolean uploadImage(File file) {
        // File không được null
        if (file == null) {
            return false;
        }

        // Lấy tên file và chuyển về chữ thường
        String fileName = file.getName().toLowerCase();

        // Chỉ cho phép file jpg, png, heic
        if (!(fileName.endsWith(".jpg")
                || fileName.endsWith(".png")
                || fileName.endsWith(".heic"))) {
            return false;
        }

        // Dung lượng file không quá 10MB
        return file.length() <= 10 * 1024 * 1024;
    }

    public static boolean submitRequests(List<Integer> serviceIds) {
        // Danh sách dịch vụ không được null hoặc rỗng
        if (serviceIds == null || serviceIds.isEmpty()) {
            return false;
        }

        // Kiểm tra từng mã dịch vụ phải lớn hơn 0
        for (Integer id : serviceIds) {
            if (id == null || id <= 0) {
                return false;
            }
        }

        return true;
    }

    // =====================================================
    // Feedback
    // ======================
     public static boolean sendFeedback(int bookingId, int value) {
        // Mã đặt phòng phải lớn hơn 0
        if (bookingId <= 0) {
            return false;
        }

        // Feedback chỉ nhận 0 hoặc 1
        // 0: không hài lòng
        // 1: hài lòng
        if (value != 0 && value != 1) {
            return false;
        }

        // Lưu feedback theo bookingId
        feedbackStore.put(bookingId, value);
        return true;
    }

    public static List<String> viewHistory(int userId, int page) {
        // userId và page phải lớn hơn 0
        if (userId <= 0 || page <= 0) {
            return Collections.emptyList();
        }

        // Dữ liệu demo lịch sử đặt phòng
        return Arrays.asList(
                "Booking A - Phòng Deluxe - 2 đêm",
                "Booking B - Phòng Standard - 1 đêm"
        );
    }

    public static boolean uploadPricingConfig(String fileName, int newVersion, int currentVersion) {
        // Tên file không được null
        if (fileName == null) {
            return false;
        }

        // File phải có định dạng .json
        if (!fileName.toLowerCase().endsWith(".json")) {
            return false;
        }

        // Phiên bản mới phải lớn hơn phiên bản hiện tại
        return newVersion > currentVersion;
    }

    public static void main(String[] args) {
        register("0123456789");
        login("0123456789", otpStore.get("0123456789"));
    }
}
