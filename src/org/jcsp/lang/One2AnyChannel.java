    //////////////////////////////////////////////////////////////////////
    //                                                                  //
    //  JCSP ("CSP for Java") Libraries                                 //
    //  Copyright (C) 1996-2008 Peter Welch and Paul Austin.            //
    //                2001-2004 Quickstone Technologies Limited.        //
    //                                                                  //
    //  This library is free software; you can redistribute it and/or   //
    //  modify it under the terms of the GNU Lesser General Public      //
    //  License as published by the Free Software Foundation; either    //
    //  version 2.1 of the License, or (at your option) any later       //
    //  version.                                                        //
    //                                                                  //
    //  This library is distributed in the hope that it will be         //
    //  useful, but WITHOUT ANY WARRANTY; without even the implied      //
    //  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR         //
    //  PURPOSE. See the GNU Lesser General Public License for more     //
    //  details.                                                        //
    //                                                                  //
    //  You should have received a copy of the GNU Lesser General       //
    //  Public License along with this library; if not, write to the    //
    //  Free Software Foundation, Inc., 59 Temple Place, Suite 330,     //
    //  Boston, MA 02111-1307, USA.                                     //
    //                                                                  //
    //  Author contact: P.H.Welch@kent.ac.uk                             //
    //                                                                  //
    //                                                                  //
    //////////////////////////////////////////////////////////////////////

package org.jcsp.lang;

/**
 * This defines the interface for a <i>one-to-any</i> Object channel,
 * safe for use by one writer and many readers.
 * <P>
 * The only methods provided are to obtain the <i>ends</i> of the channel,
 * through which all reading and writing operations are done.
 * Only an appropriate <i>channel-end</i> should be plugged into a process
 * &ndash; not the <i>whole</i> channel.
 * A process may use its external channels in one direction only
 * &ndash; either for <i>writing</i> or <i>reading</i>.
 * </P>
 * <P>Actual channels conforming to this interface are made using the relevant
 * <tt>static</tt> construction methods from {@link Channel}.
 * Channels may be {@link Channel#one2any() <i>synchronising</i>},
 * {@link Channel#one2any(org.jcsp.util.ChannelDataStore) <i>buffered</i>},
 * {@link Channel#one2any(int) <i>poisonable</i>}
 * or {@link Channel#one2any(org.jcsp.util.ChannelDataStore,int) <i>both</i>}
 * <i>(i.e. buffered and poisonable)</i>.
 * </P>
 * <H2>Description</H2>
 * <TT>One2AnyChannel</TT> is an interface for a channel which is safe
 * for use by many reading processes but only one writer.  Reading processes
 * compete with each other to use the channel.  Only one reader and the writer will
 * actually be using the channel at any one time.  This is managed by the
 * channel &ndash; user processes just read from or write to it.
 * </P>
 * <P>
 * <I>Please note that this is a safely shared channel and not
 * a broadcaster.  Currently, broadcasting has to be managed by
 * writing an active process (see {@link org.jcsp.plugNplay.DynamicDelta}
 * for an example).</I>
 * </P>
 * <P>
 * All reading processes and the writing process commit to the channel
 * (i.e. may not back off).  This means that the reading processes
 * <I>may not</I> {@link Alternative <TT>ALT</TT>} on this channel.
 * <P>
 * The default semantics of the channel is that of CSP &ndash; i.e. it is
 * zero-buffered and fully synchronised.  A reading process must wait
 * for the matching writer and vice-versa.
 * </P>
 * <P>
 * The <tt>static</tt> methods of {@link Channel} construct channels with
 * either the default semantics or with buffering to user-specified capacity
 * and a range of blocking/overwriting policies.
 * Various buffering plugins are given in the <TT>org.jcsp.util</TT> package, but
 * <I>careful users</I> may write their own.
 * </P>
 * <P>
 * The {@link Channel} methods also provide for the construction of
 * {@link Poisonable} channels and for arrays of channels.
 *
 * <H3><A NAME="Caution">Implementation Note and Caution</H3>
 * <I>Fair</I> servicing of readers to this channel depends on the <I>fair</I>
 * servicing of requests to enter a <TT>synchronized</TT> block (or method) by
 * the underlying Java Virtual Machine (JVM).  Java does not specify how threads
 * waiting to synchronize should be handled.  Currently, Sun's standard JDKs queue
 * these requests - which is <I>fair</I>.  However, there is at least one JVM
 * that puts such competing requests on a stack - which is legal but <I>unfair</I>
 * and can lead to infinite starvation.  This is a problem for <I>any</I> Java system
 * relying on good behaviour from <TT>synchronized</TT>, not just for these
 * <I>1-any</I> channels.
 *
 * @see org.jcsp.lang.Channel
 * @see org.jcsp.lang.One2OneChannel
 * @see org.jcsp.lang.Any2OneChannel
 * @see org.jcsp.lang.Any2AnyChannel
 * @see org.jcsp.util.ChannelDataStore
 *
 * @author P.D. Austin and P.H. Welch
 */
public interface One2AnyChannel
{
    /**
     * Returns the input end of the channel.
     */
    public SharedChannelInput in();

    /**
     * Returns the output end of the channel.
     */
    public ChannelOutput out();
}
