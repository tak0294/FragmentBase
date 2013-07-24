package fam.tak0294.sample;

import android.annotation.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * �w�肵���N�����̃J�����_�[��\������N���X
 */
public class CalendarView extends LinearLayout {
    @SuppressWarnings("unused")
    private static final String TAG = CalendarView.class.getSimpleName();
    
    private static final int WEEKDAYS = 7;
    private static final int MAX_WEEK = 6;
    
    // �T�̎n�܂�̗j����ێ�����
    private static final int BIGINNING_DAY_OF_WEEK = Calendar.SUNDAY;
    // �����̃t�H���g�F 
    private static final int TODAY_COLOR = Color.RED;
    // �ʏ�̃t�H���g�F
    private static final int DEFAULT_COLOR = Color.DKGRAY;
    // ���T�̔w�i�F 
    private static final int TODAY_BACKGROUND_COLOR = Color.LTGRAY;
    // �ʏ�̔w�i�F 
    private static final int DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;
    
    // �N���\������
    private TextView mTitleView; 
    
    // �T�̃��C�A�E�g
    private LinearLayout mWeekLayout;
    private ArrayList<LinearLayout> mWeeks = new ArrayList<LinearLayout>();
    
    /**
     * �R���X�g���N�^
     * 
     * @param context context
     */
    public CalendarView(Context context) {
        this(context, null);
    }
    
    /**
     * �R���X�g���N�^
     * 
     * @param context context
     * @param attrs attributeset
     */
    @SuppressLint("SimpleDateFormat")
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        
        createTitleView(context);
        createWeekViews(context);
        createDayViews(context);
    }

    /**
     * �N�����\���p�̃^�C�g���𐶐�����
     * 
     * @param context context
     */
    private void createTitleView(Context context) {
        float scaleDensity = context.getResources().getDisplayMetrics().density;
        
        mTitleView = new TextView(context);
        mTitleView.setGravity(Gravity.CENTER_HORIZONTAL); // �����ɕ\��
        mTitleView.setTextSize((int)(scaleDensity * 14));
        mTitleView.setTypeface(null, Typeface.BOLD); // ����
        mTitleView.setPadding(0, 0, 0, (int)(scaleDensity * 16));
        
        addView(mTitleView, new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    /**
     * �j���\���p�̃r���[�𐶐�����
     * 
     * @param context context
     */
    private void createWeekViews(Context context) {
        float scaleDensity = context.getResources().getDisplayMetrics().density;
        // �T�\�����C�A�E�g
        mWeekLayout = new LinearLayout(context);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, BIGINNING_DAY_OF_WEEK); // �T�̓����Z�b�g
        
        for (int i = 0; i < WEEKDAYS; i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.RIGHT); // �����ɕ\��
            textView.setPadding(0, 0, (int)(scaleDensity * 4), 0);
            
            LinearLayout.LayoutParams llp = 
                    new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
            llp.weight = 1;
            
            mWeekLayout.addView(textView, llp);
            
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        addView(mWeekLayout, new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    
    /**
     * ���t�\���p�̃r���[�𐶐�����
     * 
     * @param context context
     */
    private void createDayViews(Context context) {
        float scaleDensity = context.getResources().getDisplayMetrics().density;
        
        // �J�����_�[�� �ő�6�s�K�v
        for (int i = 0; i < MAX_WEEK; i++) {
            LinearLayout weekLine = new LinearLayout(context);
            mWeeks.add(weekLine);
            
            // 1�T�ԕ��̓��t�r���[�쐬
            for (int j = 0; j < WEEKDAYS; j++) {
                TextView dayView = new TextView(context);
                dayView.setGravity(Gravity.TOP | Gravity.RIGHT); 
                dayView.setPadding(0, (int)(scaleDensity * 4), (int)(scaleDensity * 4), 0);
                LinearLayout.LayoutParams llp = 
                        new LinearLayout.LayoutParams(0, (int)(scaleDensity * 48));
                llp.weight = 1;
                weekLine.addView(dayView, llp);
            }
            
            this.addView(weekLine, new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }
    
    /**
     * �N�ƌ����w�肵�āA�J�����_�[�̕\��������������
     * 
     * @param year �N�̎w��
     * @param month ���̎w��
     */
    public void set(int year, int month) {
        setTitle(year, month);
        setWeeks();
        setDays(year, month);
    }

    /**
     * �w�肵���N�������^�C�g���ɐݒ肷��
     * 
     * @param year �N�̎w��
     * @param month ���̎w��
     */
    @SuppressLint("SimpleDateFormat")
    private void setTitle(int year, int month) {
        Calendar targetCalendar = getTargetCalendar(year, month);
        
        // �N���t�H�[�}�b�g������
        String formatString = "yyyy/MM";//mTitleView.getContext().getString(R.string.format_month_year);
        SimpleDateFormat formatter = new SimpleDateFormat(formatString);
        mTitleView.setText(formatter.format(targetCalendar.getTime()));
    }

    /**
     * �j����ݒ肷��
     */
    @SuppressLint("SimpleDateFormat")
    private void setWeeks() {
        Calendar week = Calendar.getInstance();
        week.set(Calendar.DAY_OF_WEEK, BIGINNING_DAY_OF_WEEK); // �T�̓����Z�b�g
        SimpleDateFormat weekFormatter = new SimpleDateFormat("E"); // �j�����擾����t�H�[�}�b�^
        for (int i = 0; i < WEEKDAYS; i++) {
            TextView textView = (TextView) mWeekLayout.getChildAt(i);
            textView.setText(weekFormatter.format(week.getTime())); // �e�L�X�g�ɗj����\��
            week.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    /**
     * ���t��ݒ肵�Ă������\�b�h
     * 
     * @param year �N�̎w��
     * @param month ���̎w��
     */
    private void setDays(int year, int month) {
        Calendar targetCalendar = getTargetCalendar(year, month);
        
        int skipCount = getSkipCount(targetCalendar);
        int lastDay = targetCalendar.getActualMaximum(Calendar.DATE);
        int dayCounter = 1;
        
        Calendar todayCalendar = Calendar.getInstance();
        int todayYear  = todayCalendar.get(Calendar.YEAR);
        int todayMonth = todayCalendar.get(Calendar.MONTH);
        int todayDay   = todayCalendar.get(Calendar.DAY_OF_MONTH);
        
        for (int i = 0; i < MAX_WEEK; i++) {
            LinearLayout weekLayout = mWeeks.get(i);
            weekLayout.setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
            for (int j = 0; j < WEEKDAYS; j++) {
                TextView dayView = (TextView) weekLayout.getChildAt(j);
                
                // ���T����skipCount���c���Ă����
                if (i == 0 && skipCount > 0) {
                    dayView.setText(" ");
                    skipCount--;
                    continue;
                }
                
                // �ŏI�����傫�����
                if (lastDay < dayCounter) {
                    dayView.setText(" ");
                    continue;
                }
                
                // ���t��ݒ�
                dayView.setText(String.valueOf(dayCounter));
                
                boolean isToday = todayYear  == year  && 
                                  todayMonth == month && 
                                  todayDay   == dayCounter;
                
                if (isToday) {
                    dayView.setTextColor(TODAY_COLOR); // �ԕ���
                    dayView.setTypeface(null, Typeface.BOLD); // ����
                    weekLayout.setBackgroundColor(TODAY_BACKGROUND_COLOR); // �T�̔w�i�O���[
                } else {
                    dayView.setTextColor(DEFAULT_COLOR);
                    dayView.setTypeface(null, Typeface.NORMAL);
                }
                dayCounter++;
            }
        }
    }

    /**
     * �J�����_�[�̍ŏ��̋󔒂̌������߂�
     * 
     * @param targetCalendar �w�肵������Calendar��Instance
     * @return skipCount
     */
    private int getSkipCount(Calendar targetCalendar) {
        int skipCount; // �󔒂̌�
        int firstDayOfWeekOfMonth = targetCalendar.get(Calendar.DAY_OF_WEEK); // 1���̗j��
        if (BIGINNING_DAY_OF_WEEK > firstDayOfWeekOfMonth) {
            skipCount = firstDayOfWeekOfMonth - BIGINNING_DAY_OF_WEEK + WEEKDAYS;
        } else {
            skipCount = firstDayOfWeekOfMonth - BIGINNING_DAY_OF_WEEK;
        }
        return skipCount;
    }

    private Calendar getTargetCalendar(int year, int month) {
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.clear(); // �J�����_�[���̏�����
        targetCalendar.set(Calendar.YEAR, year);
        targetCalendar.set(Calendar.MONTH, month);
        targetCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return targetCalendar;
    }
}