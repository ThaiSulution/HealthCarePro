package app.healthcare;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;

@SuppressLint("Instantiatable")
public class SetupWeightAndHeight extends Dialog {
	Context context;
	View view;
	View backView;
	String title;
	TextView titleTextView;
	TextView lWeight;
	TextView lHeight;
	ButtonFlat buttonAccept;
	ButtonFlat buttonCancel;
	EditText tbxWeight;
	EditText tbxHeight;
	public static boolean isFinishSetup = false;
	public static boolean isCancle = false;

	String buttonCancelText;

	View.OnClickListener onAcceptButtonClickListener;
	View.OnClickListener onCancelButtonClickListener;

	public SetupWeightAndHeight(Context p_context, String p_title) {
		super(p_context, android.R.style.Theme_Translucent);
		this.context = p_context;// init Context
		this.title = p_title;
	}

	public void addCancelButton(String buttonCancelText) {
		this.buttonCancelText = buttonCancelText;
	}

	public void addCancelButton(String buttonCancelText,
			View.OnClickListener onCancelButtonClickListener) {
		this.buttonCancelText = buttonCancelText;
		this.onCancelButtonClickListener = onCancelButtonClickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_up_dialog);
		lWeight = (TextView) findViewById(R.id.l_w_weight);
		lHeight = (TextView) findViewById(R.id.l_w_height);
		view = (LinearLayout) findViewById(R.id.setup_contentDialog);
		backView = (RelativeLayout) findViewById(R.id.setup_dialog_rootView);
		backView.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() < view.getLeft()
						|| event.getX() > view.getRight()
						|| event.getY() > view.getBottom()
						|| event.getY() < view.getTop()) {
					dismiss();
				}
				return false;
			}
		});

		this.titleTextView = (TextView) findViewById(R.id.title);
		setTitle(title);

		this.tbxHeight = (EditText) findViewById(R.id.tbx_height);
		this.tbxWeight = (EditText) findViewById(R.id.tbx_weight);

		this.buttonAccept = (ButtonFlat) findViewById(R.id.button_accept);
		buttonAccept.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				if (tbxWeight.getText().length() == 0) {
					lWeight.setTextColor(R.color.red);
					return;
				}
				if (tbxHeight.getText().length() == 0) {
					lHeight.setTextColor(R.color.red);
					return;
				}
				String weight_height = tbxWeight.getText().toString() + "_"
						+ tbxHeight.getText().toString();
				returnResult(weight_height);

				if (onAcceptButtonClickListener != null)
					onAcceptButtonClickListener.onClick(v);
				finishSetup();
			}
		});

		if (buttonCancelText != null) {
			this.buttonCancel = (ButtonFlat) findViewById(R.id.button_cancel);
			this.buttonCancel.setVisibility(View.VISIBLE);
			this.buttonCancel.setText(buttonCancelText);
			buttonCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
					if (onCancelButtonClickListener != null)
						onCancelButtonClickListener.onClick(v);
				}
			});
		}
	}

	@Override
	public void show() {
		super.show();
		view.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.dialog_main_show_amination));
		backView.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.dialog_root_show_amin));
	}

	// GETERS & SETTERS
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if (title == null)
			titleTextView.setVisibility(View.GONE);
		else {
			titleTextView.setVisibility(View.VISIBLE);
			titleTextView.setText(title);
		}
	}

	public TextView getTitleTextView() {
		return titleTextView;
	}

	public void setTitleTextView(TextView titleTextView) {
		this.titleTextView = titleTextView;
	}

	public ButtonFlat getButtonAccept() {
		return buttonAccept;
	}

	public void setButtonAccept(ButtonFlat buttonAccept) {
		this.buttonAccept = buttonAccept;
	}

	public ButtonFlat getButtonCancel() {
		return buttonCancel;
	}

	public void setButtonCancel(ButtonFlat buttonCancel) {
		this.buttonCancel = buttonCancel;
	}

	public void setOnAcceptButtonClickListener(
			View.OnClickListener onAcceptButtonClickListener) {
		this.onAcceptButtonClickListener = onAcceptButtonClickListener;
		if (buttonAccept != null)
			buttonAccept.setOnClickListener(onAcceptButtonClickListener);
	}

	public void setOnCancelButtonClickListener(
			View.OnClickListener onCancelButtonClickListener) {
		this.onCancelButtonClickListener = onCancelButtonClickListener;
		if (buttonCancel != null)
			buttonCancel.setOnClickListener(onCancelButtonClickListener);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishSetup();
		isCancle = true;
	}

	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.dialog_main_hide_amination);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				view.post(new Runnable() {
					@Override
					public void run() {
						SetupWeightAndHeight.super.dismiss();
					}
				});

			}
		});
		Animation backAnim = AnimationUtils.loadAnimation(context,
				R.anim.dialog_root_hide_amin);

		view.startAnimation(anim);
		backView.startAnimation(backAnim);
	}

	public int setImageId(int id) {
		return id;
	}

	private void returnResult(String p_result) {
		StepRun.weight_weight = p_result;
		dismiss();
	}

	public void finishSetup() {
		isFinishSetup = true;
	}
}
