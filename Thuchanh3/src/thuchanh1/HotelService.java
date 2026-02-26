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
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            return false;
        }
        sendOtp(phone);
        return true;
    }

    private static void sendOtp(String phone) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(phone, otp);
        otpExpireTime.put(phone, System.currentTimeMillis() + 2 * 60 * 1000); // 2 minutes
    }

    // ======================
    // Login
    // ======================
    public static boolean login(String phone, String otp) {
        if (phone == null || otp == null) return false;

        String storedOtp = otpStore.get(phone);
        Long expireTime = otpExpireTime.get(phone);

        if (storedOtp == null || expireTime == null) return false;
        if (System.currentTimeMillis() > expireTime) return false;

        return storedOtp.equals(otp);
    }

    // ======================
    // Edit Profile
    // ======================
    public static boolean updateProfile(String name, String location) {
        if (name == null || name.trim().isEmpty()) return false;
        if (location == null || location.trim().isEmpty()) return false;
        return true;
    }

    // ======================
    // Upload ID Image
    // ======================
    public static boolean uploadImage(File file) {
        if (file == null) return false;

        String fileName = file.getName().toLowerCase();
        if (!(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".heic"))) {
            return false;
        }

        long sizeInMb = file.length() / 1024 / 1024;
        return sizeInMb <= 10;
    }

    // ======================
    // Submit Special Requests
    // ======================
    public static boolean submitRequests(List<Integer> serviceIds) {
        if (serviceIds == null || serviceIds.isEmpty()) return false;
        return true;
    }

    // ======================
    // Feedback
    // ======================
    public static boolean sendFeedback(int bookingId, int value) {
        if (bookingId <= 0) return false;
        if (value != 0 && value != 1) return false;
        feedbackStore.put(bookingId, value);
        return true;
    }

    // ======================
    // View History
    // ======================
    public static List<String> viewHistory(int userId, int page) {
        if (userId <= 0 || page <= 0) {
            return Collections.emptyList();
        }
        return Arrays.asList("Booking A", "Booking B");
    }

    // ======================
    // Upload Pricing Config
    // ======================
    public static boolean uploadPricingConfig(String fileName, int newVersion, int currentVersion) {
        if (fileName == null || !fileName.endsWith(".json")) return false;
        return newVersion > currentVersion;
    }

    // Demo
    public static void main(String[] args) {
        register("0123456789");
        login("0123456789", otpStore.get("0123456789"));
    }
}