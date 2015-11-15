# QScript
A simple dynamic scripting language
<h3>Sample Code 1:</h3>
<div style="background: #f8f8f8; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"><pre style="margin: 0; line-height: 125%">x <span style="color: #666666">=</span> [<span style="color: #666666">1</span>,<span style="color: #666666">2</span>,<span style="color: #666666">6</span>,<span style="color: #666666">4</span>,<span style="color: #666666">3</span>];
<span style="color: #008000; font-weight: bold">for</span>(i<span style="color: #666666">=0</span> to size(x)<span style="color: #666666">-1</span>) {
   <span style="color: #008000; font-weight: bold">for</span> (j<span style="color: #666666">=</span>i<span style="color: #666666">+1</span> to size(x)<span style="color: #666666">-1</span>){
      <span style="color: #008000; font-weight: bold">if</span>(x[i] <span style="color: #666666">&gt;</span> x[j]) {
         tmp <span style="color: #666666">=</span> x[j];
	       x[j] <span style="color: #666666">=</span> x[i];
         x[i] <span style="color: #666666">=</span> tmp;
    }
  }
}
<span style="color: #008000; font-weight: bold">print</span>(x);
</pre></div>


<b>Sample Output 1:</b>
<div style="background: #f8f8f8; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"><pre style="margin: 0; line-height: 125%">[<span style="color: #666666">1.0</span>, <span style="color: #666666">2.0</span>, <span style="color: #666666">3.0</span>, <span style="color: #666666">4.0</span>, <span style="color: #666666">6.0</span>]
</pre></div>


<h3>Sample Code 2:</h3>
<div style="background: #f8f8f8; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"><pre style="margin: 0; line-height: 125%"><span style="color: #008000; font-weight: bold">for</span>(x<span style="color: #666666">=1</span> to <span style="color: #666666">9</span>) {
   <span style="color: #008000; font-weight: bold">for</span>(y<span style="color: #666666">=1</span> to x) {
       z <span style="color: #666666">=</span> <span style="color: #008000; font-weight: bold">print</span>(<span style="color: #008000">str</span>(y)<span style="color: #666666">+</span><span style="color: #BA2121">&quot;*&quot;</span><span style="color: #666666">+</span><span style="color: #008000">str</span>(x) <span style="color: #666666">+</span> <span style="color: #BA2121">&quot;=&quot;</span> <span style="color: #666666">+</span> <span style="color: #008000">str</span>(y<span style="color: #666666">*</span>x) <span style="color: #666666">+</span> <span style="color: #BA2121">&quot; &quot;</span>);
   }
   z <span style="color: #666666">=</span> <span style="color: #008000; font-weight: bold">print</span>(<span style="color: #BA2121">&quot;</span><span style="color: #BB6622; font-weight: bold">\n</span><span style="color: #BA2121">&quot;</span>);
} 
</pre></div>


<b>Sample Output2:</b>
<div style="background: #f8f8f8; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"><pre style="margin: 0; line-height: 125%"><span style="color: #666666">1.0*1.0=1.0</span> 
<span style="color: #666666">1.0*2.0=2.0</span> <span style="color: #666666">2.0*2.0=4.0</span> 
<span style="color: #666666">1.0*3.0=3.0</span> <span style="color: #666666">2.0*3.0=6.0</span> <span style="color: #666666">3.0*3.0=9.0</span> 
<span style="color: #666666">1.0*4.0=4.0</span> <span style="color: #666666">2.0*4.0=8.0</span> <span style="color: #666666">3.0*4.0=12.0</span> <span style="color: #666666">4.0*4.0=16.0</span> 
<span style="color: #666666">1.0*5.0=5.0</span> <span style="color: #666666">2.0*5.0=10.0</span> <span style="color: #666666">3.0*5.0=15.0</span> <span style="color: #666666">4.0*5.0=20.0</span> <span style="color: #666666">5.0*5.0=25.0</span> 
<span style="color: #666666">1.0*6.0=6.0</span> <span style="color: #666666">2.0*6.0=12.0</span> <span style="color: #666666">3.0*6.0=18.0</span> <span style="color: #666666">4.0*6.0=24.0</span> <span style="color: #666666">5.0*6.0=30.0</span> <span style="color: #666666">6.0*6.0=36.0</span> 
<span style="color: #666666">1.0*7.0=7.0</span> <span style="color: #666666">2.0*7.0=14.0</span> <span style="color: #666666">3.0*7.0=21.0</span> <span style="color: #666666">4.0*7.0=28.0</span> <span style="color: #666666">5.0*7.0=35.0</span> <span style="color: #666666">6.0*7.0=42.0</span> <span style="color: #666666">7.0*7.0=49.0</span> 
<span style="color: #666666">1.0*8.0=8.0</span> <span style="color: #666666">2.0*8.0=16.0</span> <span style="color: #666666">3.0*8.0=24.0</span> <span style="color: #666666">4.0*8.0=32.0</span> <span style="color: #666666">5.0*8.0=40.0</span> <span style="color: #666666">6.0*8.0=48.0</span> <span style="color: #666666">7.0*8.0=56.0</span> <span style="color: #666666">8.0*8.0=64.0</span> 
<span style="color: #666666">1.0*9.0=9.0</span> <span style="color: #666666">2.0*9.0=18.0</span> <span style="color: #666666">3.0*9.0=27.0</span> <span style="color: #666666">4.0*9.0=36.0</span> <span style="color: #666666">5.0*9.0=45.0</span> <span style="color: #666666">6.0*9.0=54.0</span> <span style="color: #666666">7.0*9.0=63.0</span> <span style="color: #666666">8.0*9.0=72.0</span> <span style="color: #666666">9.0*9.0=81.0</span> 
</pre></div>




