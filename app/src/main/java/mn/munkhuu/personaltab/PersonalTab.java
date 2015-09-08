package mn.munkhuu.personaltab;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Showtime on 9/8/15.
 */
public class PersonalTab implements View.OnClickListener , View.OnTouchListener{

    public interface PersonalTabSelected{
        void seleted(int position);
    }

    private PersonalTabSelected selectListener;
    private View tabView;
    private ImageView icon;
    private ImageView selector;
    private MaterialRippleLayout background;

    private Drawable iconDrawable;

    private int iconColor;
    private int primaryColor;
    private int accentColor;

    private Resources res;

    private int position;
    private boolean active;
    private PersonalTabListener listener;


    public PersonalTab(Context ctx){

        res = ctx.getResources();
        if(deviceHaveRippleSupport()) {

            tabView = LayoutInflater.from(ctx).inflate(R.layout.material_tab_icon, null);

            icon = (ImageView) tabView.findViewById(R.id.icon);
            background = (MaterialRippleLayout) tabView.findViewById(R.id.reveal);
            selector = (ImageView) tabView.findViewById(R.id.selector);

        }else{

            tabView = LayoutInflater.from(ctx).inflate(R.layout.tab_icon, null);
            icon = (ImageView) tabView.findViewById(R.id.icon);
            selector = (ImageView) tabView.findViewById(R.id.selector);

        }

        if (deviceHaveRippleSupport()){
            tabView.setOnClickListener(this);
        }else{
            tabView.setOnTouchListener(this);
        }

        iconColor = Color.BLACK;
        active = false;
    }

    public void setTabListener(PersonalTabListener listener){
        this.listener = listener;
    }

    public void setSelectListener(PersonalTabSelected listener){
        this.selectListener = listener;
    }

    public void setAccentColor(int color){

        this.accentColor = color;
        this.iconColor = color;
    }

    public void setPrimaryColor(int color) {
        this.primaryColor = color;

        if(deviceHaveRippleSupport()) {
            background.setBackgroundColor(color);
        }
        else {
            tabView.setBackgroundColor(color);
        }

    }

    public void setIconColor(int color)
    {
        this.iconColor = color;
        if (this.icon != null)
            this.icon.setColorFilter(color);
    }

    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    public void deSelectTab() {
        // set 60% alpha to icon
        if(icon != null)
            setIconAlpha(0x99);

        this.selector.setBackgroundColor(res.getColor(android.R.color.transparent));

        active = false;
    }

    public void selectTab() {
        // set 100% alpha to icon
        if(icon != null)
            setIconAlpha(0xFF);

        this.selector.setBackgroundColor(accentColor);

        active = true;
    }

    public boolean isSelected() {
        return active;
    }

    private void setIconAlpha(int paramInt)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            this.icon.setImageAlpha(paramInt);
            return;
        }
        this.icon.setColorFilter(Color.argb(paramInt, Color.red(this.iconColor), Color.green(this.iconColor), Color.blue(this.iconColor)));
    }

    public View getTabView() {
        return tabView;
    }

    public PersonalTab setIcon(Drawable icon) {

        iconDrawable = icon;

        this.icon.setImageDrawable(icon);
        this.setIconColor(this.iconColor);
        return this;
    }

    private boolean deviceHaveRippleSupport() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    private void tabSelect(){

        if (selectListener != null){
            selectListener.seleted(this.position);
        }

        if(listener != null) {

            if(active) {
                listener.onTabReselected(this);
            }
            else {
                listener.onTabSelected(this);
            }
        }

        if(!active)
            this.selectTab();
    }

    @Override
    public void onClick(View view) {

        tabSelect();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            tabView.setBackgroundColor(Color.argb(0x80, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)));

            // do nothing
            return true;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            tabView.setBackgroundColor(primaryColor);
            return true;
        }

        // new effects
        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {

            tabView.setBackgroundColor(primaryColor);

            // set the click
            tabSelect();

            return true;
        }

        return false;
    }
}
