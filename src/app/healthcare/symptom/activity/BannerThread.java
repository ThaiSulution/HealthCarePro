 /* Copyright [2015] [Nguyễn Hoàng Vũ , Nguyễn Phi Viễn]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package app.healthcare.symptom.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class BannerThread extends Thread
{
	Context context;
	Handler handler;
	ImageView banner;
	boolean isRunning=true;
	public int time = 0;
    public int loadbanner = 1;
	String path;
	public int display = 0;
	public BannerThread(final Context context, Handler handler, ImageView view)
	{
		this.context = context;
		this.handler = handler;
		this.banner = view;
		handler.post(new Runnable() 
		{	
			@Override
            public void run() 
			{
				int icon_child = context.getResources().getIdentifier("drawable/banner" + loadbanner, null, context.getPackageName());
				Drawable drawable = context.getResources().getDrawable(icon_child);
				banner.setBackgroundDrawable(drawable);
			}
		});
	}
	
	public void putinfobanner(Intent intent)
    {
    	intent.putExtra("Time", time);
    	intent.putExtra("Banner", loadbanner);
    	intent.putExtra("Display", display);
    }
	
	@Override
	public void run()
	{
		try
		{
			while (time>=0)
			{
				while (isRunning)
				{
					sleep(1000);
					time++;
					if (time == 5)
					{
						time = 0;
						loadbanner++;
						if (loadbanner == 4)
							loadbanner = 1;
						handler.post(new Runnable() 
						{	
							@Override
                            public void run() 
							{
								int icon_child = context.getResources().getIdentifier("drawable/banner" + loadbanner, null, context.getPackageName());
								Drawable drawable = context.getResources().getDrawable(icon_child);
								banner.setBackgroundDrawable(drawable);
								banner.setOnClickListener(new View.OnClickListener() 
								{			
									@Override
                                    public void onClick(View v) 
									{
										Intent intent = null;
										
								    	path = new String("https://www.google.com");
								    	switch (loadbanner) 
								    	{
											case 1 :	intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(path));
														break;
											case 2 : 	intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(path));
														break;
											case 3 : 	intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(path));
														break;
											default:	Toast.makeText(context, "Khong hop le!", Toast.LENGTH_LONG).show();
														break;
										}
								    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								    	context.startActivity(intent);
									}
								});
							}
						});
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
	}
}